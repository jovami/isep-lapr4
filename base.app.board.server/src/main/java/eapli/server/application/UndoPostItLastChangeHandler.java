package eapli.server.application;

import eapli.base.board.domain.BoardTitle;
import eapli.board.SBProtocol;
import eapli.server.SBServerApp;
import eapli.server.application.newChangeEvent.NewChangeEvent;
import eapli.board.shared.dto.BoardRowColDTOEncoder;
import eapli.board.shared.dto.BoardWriteAccessDTOEncoder;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

/**
 * UndoPostItLastChangeHandler
 */
public class UndoPostItLastChangeHandler extends AbstractSBServerHandler {
    private final EventPublisher publisher;

    public UndoPostItLastChangeHandler(Socket sock, SBProtocol request) {
        super(sock,request);

        this.publisher = InProcessPubSub.publisher();
    }

    private void sendBoardsUserCanWrite() throws IOException {
        var user = SBServerApp.activeAuths.get(authToken)
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
            this.sendBoardsUserCanWrite();

            var request = new SBProtocol(inS);

            var decoder = new BoardRowColDTOEncoder();
            var boardInfo = decoder.decode(request.getContentAsString());

            var title = boardInfo.boardName();

            var row = boardInfo.row() + 1;
            var col = boardInfo.column() + 1;

            var board = SBServerApp.boards.get(BoardTitle.valueOf(title));

            if (board == null) {
                SBProtocol.sendErr("Board not found",outS);
                return;
            }

            var user = SBServerApp.activeAuths.get(authToken)
                    .getUserLoggedIn();

            Optional<String> opt = board.undoChangeOnPostIt(row, col, user);
            if (opt.isEmpty()) {
                SBProtocol.sendErr("Could not undo last change",outS);
                return;
            }

            var message = String.format("%s\t%d,%d\t%s", title, row, col, opt.get());

            SBProtocol protocol = new SBProtocol();
            protocol.setCode(SBProtocol.UPDATE_POST_IT);
            protocol.setContentFromString(message);

            this.publisher.publish(new NewChangeEvent(title, protocol));

            var reply = new SBProtocol();
            reply.setCode(SBProtocol.ACK);
            reply.send(outS);

            System.out.printf("[INFO] undo last change on post-it from cell(%d,%d)\n",row,col);
        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }

    }
}
