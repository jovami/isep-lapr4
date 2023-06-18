package eapli.client.application;

import eapli.board.SBProtocol;
import eapli.board.shared.dto.BoardFromToDTO;
import eapli.board.shared.dto.BoardFromToDTOEncoder;
import eapli.board.shared.dto.BoardWriteAccessDTO;
import eapli.board.shared.dto.BoardWriteAccessDTOEncoder;
import eapli.client.SBPClientApp;
import eapli.framework.application.UseCaseController;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

@UseCaseController
public class MovePostItController {
    private final Socket sock;
    private final DataInputStream inS;
    private final DataOutputStream outS;

    public MovePostItController(InetAddress serverIP, int serverPort) throws IOException {
        this.sock = new Socket(serverIP, serverPort);
        this.inS = new DataInputStream(sock.getInputStream());
        this.outS = new DataOutputStream(sock.getOutputStream());
    }

    public List<BoardWriteAccessDTO> requestBoards() throws IOException, ReceivedERRCode {
        final var requestBoards = new SBProtocol();
        requestBoards.setCode(SBProtocol.MOVE_POST_IT);
        requestBoards.setToken(SBPClientApp.authToken());

            requestBoards.send(this.outS);
            final var reply = new SBProtocol(this.inS);

            final var decoder = new BoardWriteAccessDTOEncoder();
            return decoder.decodeMany(reply.getContentAsString());
    }

    public boolean movePostIt(BoardFromToDTO dto) throws IOException, ReceivedERRCode {
        final var protocol = new SBProtocol();
        final var encoder = new BoardFromToDTOEncoder();

        protocol.setCode(SBProtocol.MOVE_POST_IT);
        protocol.setContentFromString(encoder.encode(dto));

        protocol.send(this.outS);

        final var reply = new SBProtocol(this.inS);
        return reply.getCode() == SBProtocol.ACK;
    }

    public void closeSocket() {
        try {
            this.sock.close();
        } catch (IOException ignored) {
        }
    }
}
