package eapli.board.server.application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import eapli.base.board.domain.BoardTitle;
import eapli.board.SBProtocol;
import eapli.board.server.SBPServerApp;
import eapli.board.server.application.newChangeEvent.NewChangeEvent;
import eapli.board.shared.dto.BoardRowColDTOEncoder;
import eapli.board.shared.dto.BoardWriteAccessDTOEncoder;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import eapli.framework.validations.Preconditions;
import jovami.util.exceptions.ReceivedERRCode;

/**
 * UndoPostItLastChangeHandler
 */
public class UndoPostItLastChangeHandler implements Runnable {
    private final Socket sock;
    // private final SBProtocol request;
    private final EventPublisher publisher;

    private DataInputStream inS;
    private DataOutputStream outS;

    public UndoPostItLastChangeHandler(Socket sock, SBProtocol request) {
        Preconditions.areEqual(request.getCode(), SBProtocol.UNDO_LAST_POST_IT_CHANGE);
        this.sock = sock;

        this.publisher = InProcessPubSub.publisher();
    }

    private void sendBoardsUserCanWrite() throws IOException {
        var user = SBPServerApp.activeAuths.get(this.sock.getInetAddress())
                .getUserLoggedIn();

        var boards = new ShareBoardService().boardsUserCanWrite(user);
        var body = new BoardWriteAccessDTOEncoder().encode(boards);

        var reply = new SBProtocol();
        reply.setContentFromString(body);
        reply.send(outS);
    }

    @Override
    public void run() {
        try {
            this.inS = new DataInputStream(sock.getInputStream());
            this.outS = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            this.sendBoardsUserCanWrite();

            var request = new SBProtocol(inS);

            var decoder = new BoardRowColDTOEncoder();
            var boardInfo = decoder.decode(request.getContentAsString());

            var title = boardInfo.boardName();

            // FIXME: Board class is using 1-based indexing
            var row = boardInfo.row() + 1;
            var col = boardInfo.column() + 1;

            var board = SBPServerApp.boards.get(BoardTitle.valueOf(title));

            if (board == null) {
                var err = new SBProtocol();
                err.setCode(SBProtocol.ERR);
                err.send(this.outS);
                return;
            }

            System.out.println("hi before board.undoChangeOnPostIt()");

            if (!board.undoChangeOnPostIt(row, col)) {
                var reply = new SBProtocol();
                reply.setCode(SBProtocol.ERR);
                reply.send(outS);
            }

            System.out.println("hi after board.undoChangeOnPostIt()");

            var message = String.format("%s\t%d,%d", title, row, col);
            request.setContentFromString(message); // aldrabado
            var event = new NewChangeEvent(title, request);
            this.publisher.publish(event);

            var reply = new SBProtocol();
            reply.setCode(SBProtocol.ACK);
            reply.send(outS);
        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }

    }
}
