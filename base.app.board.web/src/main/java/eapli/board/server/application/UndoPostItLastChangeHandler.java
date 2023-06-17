package eapli.board.server.application;

import eapli.base.board.domain.BoardTitle;
import eapli.board.SBProtocol;
import eapli.board.server.SBServerApp;
import eapli.board.server.application.newChangeEvent.NewChangeEvent;
import eapli.board.shared.dto.BoardRowColDTOEncoder;
import eapli.board.shared.dto.BoardWriteAccessDTOEncoder;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import eapli.framework.validations.Preconditions;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

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
        var user = SBServerApp.activeAuths.get(this.sock.getInetAddress())
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

            var board = SBServerApp.boards.get(BoardTitle.valueOf(title));

            if (board == null) {
                var err = new SBProtocol();
                err.setCode(SBProtocol.ERR);
                err.send(this.outS);
                return;
            }

            System.out.println("hi before board.undoChangeOnPostIt()");

            Optional<String> opt = board.undoChangeOnPostIt(row, col);
            if (opt.isEmpty()) {
                var reply = new SBProtocol();
                reply.setCode(SBProtocol.ERR);
                reply.send(outS);
                return;
            }

            System.out.println("hi after board.undoChangeOnPostIt()");

            var message = String.format("%s\t%d,%d\t%s", title, row, col,opt.get());

            SBProtocol protocol = new SBProtocol();
            protocol.setCode(SBProtocol.UPDATE_POST_IT);
            protocol.setContentFromString(message);

            var event = new NewChangeEvent(title, protocol);
            this.publisher.publish(event);

            var reply = new SBProtocol();
            reply.setCode(SBProtocol.ACK);
            reply.send(outS);
        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }

    }
}
