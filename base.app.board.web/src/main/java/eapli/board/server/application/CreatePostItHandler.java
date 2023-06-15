package eapli.board.server.application;

import eapli.base.board.domain.*;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.board.SBProtocol;
import eapli.board.server.MenuRequest;
import eapli.board.server.SBPServerApp;
import eapli.board.server.application.newChangeEvent.NewChangeEvent;
import eapli.board.server.domain.CreatePostIt;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CreatePostItHandler implements Runnable {

    private DataInputStream inS;
    private DataOutputStream outS;
    private final Socket sock;
    private final String alterType = "Create";
    private String alterBoard;
    private String alterPosition;
    private String alterTime;
    private String alterText;

    private final EventPublisher publisher = InProcessPubSub.publisher();
    private final BoardRepository boardRepository = PersistenceContext.repositories().boards();
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

            SystemUser user = MenuRequest.clientBySock(sock.getInetAddress()).getUserLoggedIn();

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


            //TODO: REMOVE DATABASE CALL FROM HERE
            Optional<Board> optBoard = boardRepository.ofIdentity(BoardTitle.valueOf(alterBoard));

            if (optBoard.isEmpty()){
                throw new ReceivedERRCode("Board not found");
            }
            if(checkIfAllCellsAreOccupied(optBoard.get()))
            {
                SBProtocol boardFull = new SBProtocol();
                boardFull.setCode(SBProtocol.ERR);
                boardFull.setContentFromString("Board full");
                boardFull.send(outS);
                return;
            }

            if (checkIfCellIsOccupied(optBoard.get(),alterPosition))
            {
                System.out.println("oi2");
                SBProtocol cellOccupied = new SBProtocol();
                cellOccupied.setCode(SBProtocol.ERR);
                cellOccupied.setContentFromString("Cell occupied");
                cellOccupied.send(outS);
                return;
            }


            String[] dimensions = arr[1].split(",");


            srv_postIt.createPostIt(optBoard.get(),
                            Integer.parseInt(dimensions[0]) * Integer.parseInt(dimensions[1])-1,
                                        alterText,user);

            StringBuilder sb = getStringBuilder();
            LinkedList<BoardHistory> history = SBPServerApp.histories.get(optBoard.get());
            CreatePostIt createPostIt = new CreatePostIt(optBoard.get(),String.valueOf(sb));
            history.push(createPostIt);

            NewChangeEvent event = new NewChangeEvent(optBoard.get().getBoardTitle().title(),receiveText);
            publisher.publish(event);



            SBProtocol response = new SBProtocol();
            response.setCode(SBProtocol.ACK);
            response.send(outS);


        } catch (
                IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }


    }

    private boolean checkIfCellIsOccupied(Board board, String alterPosition) {
        String[] dimensions = alterPosition.split(",");
        int row = Integer.parseInt(dimensions[0]);
        int col = Integer.parseInt(dimensions[1]);
        Cell cell = board.getCell(row*col);
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
