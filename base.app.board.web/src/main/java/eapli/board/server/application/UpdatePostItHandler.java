package eapli.board.server.application;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.domain.ChangePostIt;
import eapli.base.board.domain.CreatePostIt;
import eapli.board.SBProtocol;
import eapli.board.server.SBPServerApp;
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

public class UpdatePostItHandler implements Runnable {
    private final Socket socket;
    private final ShareBoardService svcBoard;
    private final PostItService svcPostIt;
    private final EventPublisher publisher;

    public UpdatePostItHandler(Socket socket, SBProtocol authRequest) {
        this.socket = socket;
        this.svcBoard = new ShareBoardService();
        this.svcPostIt = new PostItService();
        this.publisher = InProcessPubSub.publisher();
    }

    public void run() {
        try {
            var time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm"));

            var inS = new DataInputStream(socket.getInputStream());
            var outS = new DataOutputStream(socket.getOutputStream());

            var user = SBPServerApp.activeAuths.get(socket.getInetAddress()).getUserLoggedIn();
            var boards = svcBoard.boardsUserCanWrite(user);

            var responseSent = new SBProtocol();
            responseSent.setContentFromString(buildBoardString(boards));
            responseSent.send(outS);

            var receivedText = new SBProtocol(inS);
            var arr = receivedText.getContentAsString().split("\t");

            var board = SBPServerApp.boards.get(BoardTitle.valueOf(arr[0]));
            if (board == null)
                throw new ReceivedERRCode("Board not found");

            var dimensions = arr[1].split(",");
            int row = Integer.parseInt(dimensions[0]);
            int column = Integer.parseInt(dimensions[1]);

            //TODO: this should be synchronized
            var cell = board.getCell(row, column);
            if (!cell.hasPostIt()) {
                var protocol = new SBProtocol();
                protocol.setCode(SBProtocol.ERR);
                protocol.setContentFromString("Cell is empty");
                protocol.send(outS);
                return;
            }
            var prevText = cell.getPostIt().getData();

            if (!svcPostIt.updatePostIt(board, row, column, arr[2], user)) {
                var protocol = new SBProtocol();
                protocol.setCode(SBProtocol.ERR);
                protocol.setContentFromString("Cell is empty");
                protocol.send(outS);
                return;
            }

            SBPServerApp.histories.get(board).push(
                    new ChangePostIt(getUpdateString(arr[0], arr[1], prevText, arr[2], time)));

            publisher.publish(new NewChangeEvent(board.getBoardTitle().title(), receivedText));

            var response = new SBProtocol();
            response.setCode(SBProtocol.ACK);
            response.send(outS);
        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }
    }

    private String getUpdateString(String alterBoard, String alterPosition, String prevText, String alterText, String alterTime) {
        return "UPDATE" + "\t" + alterBoard + "\t" + alterPosition + "\t" + alterTime + "\t" + prevText + "\t" + alterText;
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