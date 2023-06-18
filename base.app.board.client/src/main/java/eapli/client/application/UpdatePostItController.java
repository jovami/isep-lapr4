package eapli.client.application;

import eapli.board.SBProtocol;
import eapli.board.shared.dto.*;
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
public class UpdatePostItController {
    private final Socket sock;
    private final DataInputStream inS;
    private final DataOutputStream outS;

    public UpdatePostItController(InetAddress serverIP, int serverPort) throws IOException {
        this.sock = new Socket(serverIP, serverPort);
        this.inS = new DataInputStream(sock.getInputStream());
        this.outS = new DataOutputStream(sock.getOutputStream());
    }

    public List<BoardWriteAccessDTO> requestBoards() throws IOException, ReceivedERRCode {
        final var requestBoards = new SBProtocol();
        requestBoards.setCode(SBProtocol.UPDATE_POST_IT);
        requestBoards.setToken(SBPClientApp.authToken());


        requestBoards.send(this.outS);
        final var reply = new SBProtocol(this.inS);

        final var decoder = new BoardWriteAccessDTOEncoder();
        return decoder.decodeMany(reply.getContentAsString());

    }

    public boolean updatePostIt(BoardRowColDataDTO dto) throws IOException, ReceivedERRCode {
        final var protocol = new SBProtocol();
        final var encoder = new BoardRowColDataDTOEncoder();

        protocol.setCode(SBProtocol.UPDATE_POST_IT);
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