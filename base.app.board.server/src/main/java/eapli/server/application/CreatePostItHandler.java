package eapli.server.application;

import eapli.base.board.domain.*;
import eapli.board.SBProtocol;
import eapli.server.SBServerApp;
import eapli.server.application.newChangeEvent.NewChangeEvent;
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

public class CreatePostItHandler extends AbstractSBServerHandler {

    private final String alterType = "CREATE";
    private String alterBoard;
    private String alterPosition;
    private String alterTime;
    private String alterText;
    private final EventPublisher publisher = InProcessPubSub.publisher();
    private ShareBoardService srv_board;

    public CreatePostItHandler(Socket socket, SBProtocol authRequest) {
        super(socket,authRequest);
        srv_board = new ShareBoardService();
    }

    public void run() {
        try {
            inS = new DataInputStream(sock.getInputStream());
            outS = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {

            SystemUser user = SBServerApp.activeAuths.get(authToken).getUserLoggedIn();

            var boards = srv_board.boardsUserCanWrite(user);


            StringBuilder builder = new StringBuilder();
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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm:ss");
            alterTime = LocalDateTime.now().format(formatter);

            alterText = arr[2];

            Board optBoard = SBServerApp.boards.get(BoardTitle.valueOf(alterBoard));

            if(optBoard==null){
                SBProtocol.sendErr("Board not found", outS);
                return;
            }
            if(checkIfAllCellsAreOccupied(optBoard)){
                SBProtocol.sendErr("Board full",outS);
                return;
            }

            if (checkIfCellIsOccupied(optBoard,alterPosition)){
                SBProtocol.sendErr("Cell occupied",outS);
                return;
            }

            String[] dimensions = arr[1].split(",");

            if (!optBoard.addPostIt(Integer.parseInt(dimensions[0]),Integer.parseInt(dimensions[1]),new PostIt(user,alterText))){
                SBProtocol.sendErr("Cell is full",outS);
                return;
            }

            //StringBuilder sb = getStringBuilder();
            //var history = SBPServerApp.histories.get(optBoard);
            //history.push(new CreatePostIt(String.valueOf(sb)));

            NewChangeEvent event = new NewChangeEvent(optBoard.getBoardTitle().title(),receiveText);
            publisher.publish(event);

            SBProtocol response = new SBProtocol();
            response.setCode(SBProtocol.ACK);
            response.send(outS);

            System.out.println("[INFO] post-it created successfully on "+alterBoard);


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
