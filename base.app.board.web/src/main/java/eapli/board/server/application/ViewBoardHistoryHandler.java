package eapli.board.server.application;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.board.SBProtocol;
import eapli.board.server.SBPServerApp;
import eapli.board.server.domain.BoardHistory;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public class ViewBoardHistoryHandler {
    private DataInputStream inS;
    private DataOutputStream outS;
    private final Socket sock;

    private final BoardRepository boardRepository = PersistenceContext.repositories().boards();

    public ViewBoardHistoryHandler(Socket socket, SBProtocol authRequest) {
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

            for (Board b : boardRepository.findAll()) {
                builder.append(b.getBoardTitle().title());
                builder.append(" ");
            }
            ;
            SBProtocol responseSent = new SBProtocol();
            responseSent.setContentFromString(builder.toString());
            responseSent.send(outS);




            SBProtocol receiveBoard = new SBProtocol(inS);
            String board = receiveBoard.getContentAsString();

            Optional<Board> optBoard = boardRepository.ofIdentity(BoardTitle.valueOf(board));
            if (optBoard.isEmpty()) {
                throw new ReceivedERRCode("Board not found");
            }


            BoardHistory history = SBPServerApp.boardHistory.get(optBoard.get());

            StringBuilder historyBuilder = new StringBuilder();
            for (String str :history.getHistory()) {
                historyBuilder.append(str);
                historyBuilder.append(" ");
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
