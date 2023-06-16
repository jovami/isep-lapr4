package eapli.board.server.application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.board.SBProtocol;
import eapli.board.server.SBPServerApp;
import eapli.base.board.domain.BoardHistory;
import jovami.util.exceptions.ReceivedERRCode;

public class ViewBoardHistoryHandler {
    private DataInputStream inS;
    private DataOutputStream outS;
    private final Socket sock;

    public ViewBoardHistoryHandler(Socket socket, SBProtocol request) {
        this.sock = socket;
    }

    public void run() {
        try {
            inS = new DataInputStream(sock.getInputStream());
            outS = new DataOutputStream(sock.getOutputStream());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {

            StringBuilder builder = new StringBuilder();

            for (Board b : SBPServerApp.boards.values()) {
                builder.append(b.getBoardTitle().title());
                builder.append("/r");
            }
            ;
            SBProtocol responseSent = new SBProtocol();
            responseSent.setContentFromString(builder.toString());
            responseSent.send(outS);

            SBProtocol receiveBoard = new SBProtocol(inS);
            String board = receiveBoard.getContentAsString();

            Board optBoard = SBPServerApp.boards.get(BoardTitle.valueOf(board));
            if (optBoard == null) {
                throw new ReceivedERRCode("Board not found");
            }

            var history = SBPServerApp.histories.get(optBoard);

            StringBuilder historyBuilder = new StringBuilder();
            for (BoardHistory bh : history) {
                historyBuilder.append(bh.toString());
                historyBuilder.append("\r");
            }

            SBProtocol response = new SBProtocol();
            response.setCode(SBProtocol.VIEW_BOARD_HISTORY);
            response.setContentFromString(historyBuilder.toString());
            response.send(outS);

        } catch (
                IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }

    }
}
