package eapli.board.client.application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import eapli.board.SBProtocol;
import eapli.board.client.dto.BoardRowColDTO;
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
    // private List<String> boardColumns;

    public UndoPostItLastChangeController(InetAddress serverIP, int serverPort) {
        try {
            this.sock = new Socket(serverIP, serverPort);
            this.inS = new DataInputStream(sock.getInputStream());
            this.outS = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            System.out.println("Failed to connect to provided SERVER-ADDRESS and SERVER-PORT.");
            System.out.println("Application aborted.");
        }
    }

    public List<String> requestBoards() {
        SBProtocol requestAllBoard = new SBProtocol();
        requestAllBoard.setCode(SBProtocol.VIEW_ALL_BOARDS);
        try {
            requestAllBoard.send(outS);
            SBProtocol receiveBoards = new SBProtocol(inS);
            return Arrays.asList(receiveBoards.getContentAsString().split("\0"));
        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }
    }

    private String formatSendMessage(BoardRowColDTO dto) {
        var sb = new StringBuilder();
        sb.append(dto.boardName());
        sb.append("\t");
        sb.append(dto.row());
        sb.append("\t");
        sb.append(dto.column());

        sb.append("\0");
        return sb.toString();
    }

    public boolean undoChange(BoardRowColDTO dto) throws ReceivedERRCode, IOException {
        var protocol = new SBProtocol();
        protocol.setCode(SBProtocol.UNDO_LAST_POST_IT_CHANGE);
        protocol.setContentFromString(this.formatSendMessage(dto));

        try {
            protocol.send(this.outS);

            var reply = new SBProtocol(this.inS);
            if (reply.getCode() != SBProtocol.ACK)
                return false;
            return true;
        } finally {
            this.sock.close();
        }
    }

}
