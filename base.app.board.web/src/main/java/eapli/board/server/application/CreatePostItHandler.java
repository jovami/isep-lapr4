package eapli.board.server.application;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.board.SBProtocol;
import eapli.framework.validations.Preconditions;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public class CreatePostItHandler implements Runnable {

    private DataInputStream inS;
    private DataOutputStream outS;
    private final Socket sock;

    private final BoardRepository boardRepository = PersistenceContext.repositories().boards();

    public CreatePostItHandler(Socket socket, SBProtocol authRequest) {
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
                builder.append("\t");
                builder.append(b.getNumRows());
                builder.append("\t");
                builder.append(b.getNumColumns());
                //TODO: understand why '/0' is not working
                builder.append(' ');
            }
            ;
            SBProtocol responseSent = new SBProtocol();
            System.out.println(builder.toString());
            responseSent.setContentFromString(builder.toString());
            responseSent.send(outS);



            SBProtocol receiveText = new SBProtocol(inS);
            String text = receiveText.getContentAsString();
            String[] arr = text.split("\t");

            String boardName = arr[0];
            Optional<Board> optBoard = boardRepository.ofIdentity(BoardTitle.valueOf(boardName));

            String[] dimensions = arr[1].split(",");
            optBoard.ifPresent(board ->
                    board.getCells().get(Integer.parseInt(dimensions[0]) * Integer.parseInt(dimensions[1])).createPostIt(arr[2]));


            SBProtocol response = new SBProtocol();
            response.setCode(SBProtocol.ACK);
            response.send(outS);



        } catch (
                IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }


    }


}
