package eapli.board.server.application;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import eapli.base.board.domain.BoardTitle;
import eapli.board.SBProtocol;
import eapli.board.server.SBPServerApp;
import eapli.board.server.application.newChangeEvent.NewChangeEvent;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import eapli.framework.validations.Preconditions;

/**
 * UndoPostItLastChangeHandler
 */
public class UndoPostItLastChangeHandler implements Runnable {
    private final Socket sock;
    private final SBProtocol request;
    private final EventPublisher publisher;

    private DataOutputStream outS;

    public UndoPostItLastChangeHandler(Socket sock, SBProtocol request) {
        Preconditions.areEqual(request.getCode(), SBProtocol.UNDO_LAST_POST_IT_CHANGE);
        this.request = request;
        this.sock = sock;

        this.publisher = InProcessPubSub.publisher();
    }

    @Override
    public void run() {
        try {
            this.outS = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {

            var boardInfo = request.getContentAsString().split("\t");

            var boardName = boardInfo[0];
            var row = Integer.parseInt(boardInfo[1]);
            var col = Integer.parseInt(boardInfo[2]);

            var board = SBPServerApp.boards.get(BoardTitle.valueOf(boardName));

            if (board == null) {
                var err = new SBProtocol();
                err.setCode(SBProtocol.ERR);
                err.send(this.outS);
                return;
            }

            if (board.undoChangeOnPostIt(row, col)) {
                var message = String.format("%s\t%d,%d", boardName, row, col);
                // aldrabado
                request.setContentFromString(message);
                var event = new NewChangeEvent(board.getBoardTitle().title(), request);
                this.publisher.publish(event);
            }

            var reply = new SBProtocol();
            reply.setCode(SBProtocol.ACK);
            reply.send(outS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
