package eapli.server.application;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardHistory;
import eapli.base.board.domain.BoardTitle;
import eapli.board.SBProtocol;
import eapli.server.SBServerApp;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.Socket;

public class ViewBoardHistoryHandler extends AbstractSBServerHandler{

    public ViewBoardHistoryHandler(Socket socket, SBProtocol request) {
        super(socket, request);
        this.sock = socket;
    }

    public void run() {
        try {

            StringBuilder builder = new StringBuilder();
            ViewBoardRequestService srv = new ViewBoardRequestService();
            Iterable<Board> boards = srv.listReadableNonArchivedBoardsForUser(SBServerApp.activeAuths.
                    get(authToken).getUserLoggedIn());

            for (Board board : boards) {
                builder.append(board.getBoardTitle().title());
                builder.append("/r");
            }
            ;
            SBProtocol responseSent = new SBProtocol();
            responseSent.setContentFromString(builder.toString());
            responseSent.send(outS);

            SBProtocol receiveBoard = new SBProtocol(inS);
            String board = receiveBoard.getContentAsString();

            Board optBoard = SBServerApp.boards.get(BoardTitle.valueOf(board));
            if (optBoard == null) {
                throw new ReceivedERRCode("Board not found");
            }


            StringBuilder historyBuilder = new StringBuilder();
            for (BoardHistory bh :optBoard.getHistory()) {
                historyBuilder.append(bh.toString());
                historyBuilder.append("\r");
            }

            SBProtocol response = new SBProtocol();
            response.setCode(SBProtocol.VIEW_BOARD_HISTORY);
            response.setContentFromString(historyBuilder.toString());
            response.send(outS);
            System.out.printf("[INFO] board history was requested by %s\n",SBServerApp.activeAuths.get(authToken).getUserLoggedIn().username().toString());

        } catch (
                IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }

    }
}
