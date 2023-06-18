package eapli.server.application;

import eapli.base.board.domain.BoardTitle;
import eapli.board.SBProtocol;
import eapli.board.shared.dto.BoardFromToDTOEncoder;
import eapli.board.shared.dto.BoardWriteAccessDTOEncoder;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import eapli.server.SBServerApp;
import eapli.server.application.newChangeEvent.NewChangeEvent;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.Socket;

public class MovePostItHandler extends AbstractSBServerHandler{
    private final EventPublisher publisher;

    public MovePostItHandler(Socket socket, SBProtocol authRequest) {
        super(socket, authRequest);
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

            var decoder = new BoardFromToDTOEncoder();
            var boardInfo = decoder.decode(request.getContentAsString());

            var title = boardInfo.boardName();

            var rowFrom = boardInfo.rowFrom() + 1;
            var colFrom = boardInfo.columnFrom() + 1;
            var rowTo = boardInfo.rowTo() + 1;
            var colTo = boardInfo.columnTo() + 1;

            var board = SBServerApp.boards.get(BoardTitle.valueOf(title));
            if (board == null) {
                SBProtocol.sendErr("Board not found",outS);
                return;
            }

            var user = SBServerApp.activeAuths.get(authToken).getUserLoggedIn();

            var data = board.movePostIt(rowFrom, colFrom, rowTo, colTo, user);
            if (data.isEmpty()) {
                SBProtocol.sendErr("Could not move post-it",outS);
                return;
            }

            var message = String.format("%s\t%d\t%d\t%d\t%d", title, rowFrom, colFrom, rowTo, colTo);
            var protocol = new SBProtocol();
            protocol.setCode(SBProtocol.MOVE_POST_IT);
            protocol.setContentFromString(message);

            this.publisher.publish(new NewChangeEvent(title, protocol));

            var reply = new SBProtocol();
            reply.setCode(SBProtocol.ACK);
            reply.send(outS);

            System.out.printf("[INFO] Moved post it from cell(%d,%d) to cell(%d,%d)\n",rowFrom,colFrom,rowTo,colFrom);

        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }
    }
}
