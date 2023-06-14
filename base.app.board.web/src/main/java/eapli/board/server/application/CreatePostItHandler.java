package eapli.board.server.application;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.domain.Cell;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.board.SBProtocol;
import eapli.board.server.application.newChangeEvent.NewChangeEvent;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import eapli.board.server.SBPServerApp;
import eapli.board.server.domain.BoardHistory;
import eapli.board.server.MenuRequest;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.validations.Preconditions;
import jovami.util.exceptions.ReceivedERRCode;
import org.springframework.format.datetime.standard.DateTimeFormatterFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private final EventPublisher publisher =InProcessPubSub.publisher();
    private final BoardRepository boardRepository = PersistenceContext.repositories().boards();
    private ShareBoardService srv_board;

    private PostItService srv_postIt;

    public CreatePostItHandler(Socket socket, SBProtocol authRequest) {
        //Preconditions.areEqual(authRequest.getCode(), SBProtocol.CREATE_POST_IT);
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

            //SBProtocol sendBoards = sendBoardOwned(builder, boards);
            //if (sendBoards == null) return;


            for (Board b : boards) {
                //builder.append('\n');
                builder.append(b.getBoardTitle().title());
                builder.append("\t");
                builder.append(b.getNumRows());
                builder.append("\t");
                builder.append(b.getNumColumns());
                //TODO: understand why '/0' is not working
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

            Optional<Board> optBoard = boardRepository.ofIdentity(BoardTitle.valueOf(alterBoard));

            if(checkIfAllCellsAreOccupied(optBoard.get()))
            {
                SBProtocol boardFull = new SBProtocol();
                boardFull.setCode(SBProtocol.ERR);
                boardFull.setContentFromString("Board full");
                boardFull.send(outS);
                return;
            }


            String[] dimensions = arr[1].split(",");
            if (optBoard.isEmpty()){
                throw new ReceivedERRCode("Board not found");
            }
            //TODO:verify if cell is empty - method on board(to get cell)
            optBoard.get().getCells().get(
                    (Integer.parseInt(dimensions[0]) * Integer.parseInt(dimensions[1]))-1).createPostIt(alterText);

            /*if(!optBoard.isEmpty())
                srv_postIt.createPostIt(optBoard.get(),Integer.parseInt(dimensions[0]) * Integer.parseInt(dimensions[1]),arr[2], user);*/


            StringBuilder sb = getStringBuilder();
            SBPServerApp.boardHistory.putIfAbsent(optBoard.get().getBoardTitle().title(), new BoardHistory());
            BoardHistory history = SBPServerApp.boardHistory.get(optBoard.get().getBoardTitle().title());
            history.add(sb.toString());


            SBProtocol response = new SBProtocol();
            response.setCode(SBProtocol.ACK);
            response.send(outS);


        } catch (
                IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }


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

    private SBProtocol sendBoardOwned(StringBuilder builder, List<Board> boards) throws IOException {
        //send boards that the user owns
        SBProtocol sendBoards = new SBProtocol();
        if (boards.isEmpty()) {
            sendBoards.setCode(SBProtocol.ERR);
            sendBoards.setContentFromString("User does not own boards that can possible be archived");
            sendBoards.send(outS);
            return null;
        } else {
            for (Board b : boards) {
                builder.append(b.getBoardTitle().title()).append('\0');
            }
            //sendBoards.setCode(SBPMessage.SEND_BOARDS);
            String send = builder.toString();
            //sendBoards.setCode(255);
            sendBoards.setContentFromString(builder.toString());
        }
        sendBoards.send(outS);
        return sendBoards;
    }


}
