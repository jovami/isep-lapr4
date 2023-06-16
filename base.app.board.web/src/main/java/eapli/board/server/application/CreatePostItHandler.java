package eapli.board.server.application;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.domain.Cell;
import eapli.board.SBProtocol;
import eapli.board.server.SBPServerApp;
import eapli.board.server.application.newChangeEvent.NewChangeEvent;
import eapli.base.board.domain.BoardHistory;
import eapli.base.board.domain.CreatePostIt;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreatePostItHandler implements Runnable {

    private DataInputStream inS;
    private DataOutputStream outS;
    private final Socket sock;
    private final String alterType = "CREATE";
    private String alterBoard;
    private String alterPosition;
    private String alterTime;
    private String alterText;
    private final EventPublisher publisher = InProcessPubSub.publisher();
    private ShareBoardService srv_board;
    private PostItService srv_postIt;

    public CreatePostItHandler(Socket socket, SBProtocol authRequest) {
        this.sock = socket;
        srv_board = new ShareBoardService();
        srv_postIt = new PostItService();
    }

    public void run() {
        try {
            inS = new DataInputStream(sock.getInputStream());
            outS = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {

            SystemUser user = SBPServerApp.activeAuths.get(sock.getInetAddress()).getUserLoggedIn();

            StringBuilder builder = new StringBuilder();
            List<Board> boards = srv_board.listBoardsUserParticipatesAndHasWritePermissionsPlusBoardOwnsNotArchived(user);


            for (Board b : boards) {
                builder.append(b.getBoardTitle().title());
                builder.append("\t");
                builder.append(b.getNumRows());
                builder.append("\t");
                builder.append(b.getNumColumns());
                builder.append(' ');
            }

            SBProtocol responseSent = new SBProtocol();
            responseSent.setContentFromString(builder.toString());
            responseSent.send(outS);


            //Board\tROW,COL\text
            SBProtocol receiveText = new SBProtocol(inS);
            String text = receiveText.getContentAsString();
            String[] arr = text.split("\t");
            alterBoard = arr[0];
            alterPosition = arr[1];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm");
            alterTime = LocalDateTime.now().format(formatter);

            alterText = arr[2];

            Board optBoard = SBPServerApp.boards.get(BoardTitle.valueOf(alterBoard));

            if (optBoard==null){
                throw new ReceivedERRCode("Board not found");
            }
            if(checkIfAllCellsAreOccupied(optBoard))
            {
                SBProtocol boardFull = new SBProtocol();
                boardFull.setCode(SBProtocol.ERR);
                boardFull.setContentFromString("Board full");
                boardFull.send(outS);
                return;
            }

            if (checkIfCellIsOccupied(optBoard,alterPosition))
            {
                SBProtocol cellOccupied = new SBProtocol();
                cellOccupied.setCode(SBProtocol.ERR);
                cellOccupied.setContentFromString("Cell occupied");
                cellOccupied.send(outS);
                return;
            }

            String[] dimensions = arr[1].split(",");
            if (!srv_postIt.createPostIt(optBoard,
                            Integer.parseInt(dimensions[0]),Integer.parseInt(dimensions[1]),
                                        alterText,user)){
                SBProtocol response = new SBProtocol();
                response.setCode(SBProtocol.ERR);
                response.setContentFromString("Cell is full");
                response.send(outS);
                return;
            }

            StringBuilder sb = getStringBuilder();
            var history = SBPServerApp.histories.get(optBoard);
            history.push(new CreatePostIt(optBoard,String.valueOf(sb)));

            NewChangeEvent event = new NewChangeEvent(optBoard.getBoardTitle().title(),receiveText);
            publisher.publish(event);



            SBProtocol response = new SBProtocol();
            response.setCode(SBProtocol.ACK);
            response.send(outS);


        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }


    }

    private boolean checkIfCellIsOccupied(Board board, String alterPosition) {
        String[] dimensions = alterPosition.split(",");
        int row = Integer.parseInt(dimensions[0]);
        int col = Integer.parseInt(dimensions[1]);
        Cell cell = board.getCell(row,col);
        return cell.hasPostIt();
    }

    private StringBuilder getStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append(alterType);
        sb.append("\t");
        sb.append(alterBoard);
        sb.append("\t");
        sb.append(alterPosition);
        sb.append("\t");
        sb.append(alterTime);
        sb.append("\t");
        sb.append(alterText);
        return sb;
    }

    private boolean checkIfAllCellsAreOccupied(Board board)
    {
        for(Cell cell : board.getCells())
        {
            if(!cell.hasPostIt())
                return false;
        }
        return true;
    }



}
