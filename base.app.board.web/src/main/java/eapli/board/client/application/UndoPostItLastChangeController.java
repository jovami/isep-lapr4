package eapli.board.client.application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import eapli.board.SBProtocol;
import eapli.board.shared.dto.BoardRowColDTO;
import eapli.board.shared.dto.BoardRowColDTOEncoder;
import eapli.board.shared.dto.BoardWriteAccessDTO;
import eapli.board.shared.dto.BoardWriteAccessDTOEncoder;
import eapli.framework.application.UseCaseController;
import jovami.util.exceptions.ReceivedERRCode;

/**
 * UndoPostItLastChangeController
 */
@UseCaseController
public class UndoPostItLastChangeController {

    private Socket sock;
    private DataInputStream inS;
    private DataOutputStream outS;

    public UndoPostItLastChangeController(InetAddress serverIP, int serverPort) {
        try {
            this.sock = new Socket(serverIP, serverPort);
            this.inS = new DataInputStream(sock.getInputStream());
            this.outS = new DataOutputStream(sock.getOutputStream());
        } catch (final IOException e) {
            System.out.println("Failed to connect to provided SERVER-ADDRESS and SERVER-PORT.");
            System.out.println("Application aborted.");
        }
    }

    public List<BoardWriteAccessDTO> requestBoards() {
        final var requestBoards = new SBProtocol();
        requestBoards.setCode(SBProtocol.UNDO_LAST_POST_IT_CHANGE);
        try {
            requestBoards.send(outS);
            final var reply = new SBProtocol(inS);

            final var decoder = new BoardWriteAccessDTOEncoder();
            return decoder.decodeMany(reply.getContentAsString());
        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }
    }

    public boolean undoChange(BoardRowColDTO dto) throws ReceivedERRCode, IOException {
        final var protocol = new SBProtocol();
        final var encoder = new BoardRowColDTOEncoder();

        protocol.setCode(SBProtocol.UNDO_LAST_POST_IT_CHANGE);
        protocol.setContentFromString(encoder.encode(dto));

        try {
            protocol.send(this.outS);

            final var reply = new SBProtocol(this.inS);
            if (reply.getCode() != SBProtocol.ACK)
                return false;
            return true;
        } finally {
            this.sock.close();
        }
    }

}
