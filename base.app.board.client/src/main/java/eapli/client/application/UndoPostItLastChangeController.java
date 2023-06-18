package eapli.client.application;

import eapli.board.SBProtocol;
import eapli.client.SBPClientApp;
import eapli.board.shared.dto.BoardRowColDTO;
import eapli.board.shared.dto.BoardRowColDTOEncoder;
import eapli.board.shared.dto.BoardWriteAccessDTO;
import eapli.board.shared.dto.BoardWriteAccessDTOEncoder;
import eapli.framework.application.UseCaseController;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

/**
 * UndoPostItLastChangeController
 */
@UseCaseController
public final class UndoPostItLastChangeController {

    private Socket sock;
    private DataInputStream inS;
    private DataOutputStream outS;

    public UndoPostItLastChangeController(InetAddress serverIP, int serverPort) throws IOException {

        this.sock = new Socket(serverIP, serverPort);
        this.inS = new DataInputStream(sock.getInputStream());
        this.outS = new DataOutputStream(sock.getOutputStream());

    }

    public List<BoardWriteAccessDTO> requestBoards() throws IOException, ReceivedERRCode {
        final var requestBoards = new SBProtocol();
        requestBoards.setCode(SBProtocol.UNDO_LAST_POST_IT_CHANGE);
        requestBoards.setToken(SBPClientApp.authToken());

        requestBoards.send(outS);
        final var reply = new SBProtocol(inS);

        final var decoder = new BoardWriteAccessDTOEncoder();
        return decoder.decodeMany(reply.getContentAsString());
    }

    public boolean undoChange(BoardRowColDTO dto) throws ReceivedERRCode, IOException {
        final var protocol = new SBProtocol();
        final var encoder = new BoardRowColDTOEncoder();

        protocol.setCode(SBProtocol.UNDO_LAST_POST_IT_CHANGE);
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
