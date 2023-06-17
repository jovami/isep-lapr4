package eapli.board.server.application;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.board.SBProtocol;
import eapli.board.server.SBServerApp;
import eapli.board.server.application.newChangeEvent.NewChangeEvent;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MovePostItHandler {
    private final Socket socket;
    private final ShareBoardService svcBoard;
    private final PostItService svcPostIt;
    private final EventPublisher publisher;

    public MovePostItHandler(Socket socket, SBProtocol authRequest) {
        this.socket = socket;
        this.svcBoard = new ShareBoardService();
        this.svcPostIt = new PostItService();
        this.publisher = InProcessPubSub.publisher();
    }

    public void run() {
        try {
            var inS = new DataInputStream(socket.getInputStream());
            var outS = new DataOutputStream(socket.getOutputStream());

            var time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm"));

            var user = SBServerApp.activeAuths.get(socket.getInetAddress()).getUserLoggedIn();
            var boards = svcBoard.boardsUserCanWrite(user);

            var responseSent = new SBProtocol();
            responseSent.setContentFromString(buildBoardString(boards));
            responseSent.send(outS);

            var receivedText = new SBProtocol(inS);
            var arr = receivedText.getContentAsString().split("\t");

            var board = SBServerApp.boards.get(BoardTitle.valueOf(arr[0]));
            if (board == null)
                throw new ReceivedERRCode("Board not found");

            int rowFrom = Integer.parseInt(arr[1]);
            int columnFrom = Integer.parseInt(arr[2]);
            int rowTo = Integer.parseInt(arr[3]);
            int columnTo = Integer.parseInt(arr[4]);

            if (!svcPostIt.movePostIt(board, rowFrom, columnFrom, rowTo, columnTo, user)) {
                var protocol = new SBProtocol();
                protocol.setCode(SBProtocol.ERR);
                protocol.setContentFromString("Error moving Post-It");
                protocol.send(outS);
                return;
            }

            publisher.publish(new NewChangeEvent(board.getBoardTitle().title(), receivedText));

            var reply = new SBProtocol();
            reply.setCode(SBProtocol.ACK);
            reply.send(outS);
        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }
    }

    private String createString(String alterBoard, int row, int column, String alterText, String alterTime) {
        return "CREATE"
                + "\t"
                + alterBoard
                + "\t"
                + row + "," + column
                + "\t"
                + alterTime
                + "\t"
                + alterText;
    }

    private String removeString(String alterBoard, int row, int column, String alterText, String alterTime) {
        return "REMOVE"
                + "\t"
                + alterBoard
                + "\t"
                + row + "," + column
                + "\t"
                + alterTime
                + "\t"
                + alterText;
    }

    public String buildBoardString(Iterable<Board> boards) {
        var builder = new StringBuilder();
        for (var b : boards) {
            builder.append(b.getBoardTitle().title());
            builder.append("\t");
            builder.append(b.getNumRows());
            builder.append("\t");
            builder.append(b.getNumColumns());
            builder.append(' ');
        }
        return builder.toString();
    }
}
