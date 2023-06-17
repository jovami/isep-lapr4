package eapli.board.client.application;

import eapli.board.SBProtocol;
import eapli.board.client.dto.MovePostItDTO;
import eapli.framework.application.UseCaseController;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UseCaseController
public class MovePostItController {
    private final Socket socket;
    private final DataInputStream inS;
    private final DataOutputStream outS;

    public MovePostItController(InetAddress serverIP, int serverPort) {
        try {
            this.socket = new Socket(serverIP, serverPort);
            this.inS = new DataInputStream(socket.getInputStream());
            this.outS = new DataOutputStream(socket.getOutputStream());
        } catch (IOException | RuntimeException e) {
            System.out.println("Failed to connect to provided SERVER-ADDRESS and SERVER-PORT.");
            System.out.println("Application aborted.");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> listBoardString() {
        try {
            var protocol = new SBProtocol();
            protocol.setCode(SBProtocol.MOVE_POST_IT);
            protocol.send(outS);

            var boardColumns = new ArrayList<>(List.of(new SBProtocol(inS).getContentAsString().split(" ")));
            var boards = new ArrayList<String>();

            boardColumns.forEach(column -> boards.addAll(Arrays.asList(column.split("\t"))));

            return boards;
        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }
    }

    public boolean movePostIt(MovePostItDTO dto) throws IOException, ReceivedERRCode {
        var protocol = new SBProtocol();
        protocol.setCode(SBProtocol.MOVE_POST_IT);
        protocol.setContentFromString(this.formatSendMessage(dto));

        try {
            protocol.send(outS);

            var reply = new SBProtocol(inS);
            return reply.getCode() == SBProtocol.ACK;
        } finally {
            this.socket.close();
        }
    }

    private String formatSendMessage(MovePostItDTO dto) {
        return  dto.boardName() + "\t" + dto.rowFrom() + "\t" + dto.columnFrom() + "\t" +
                dto.rowTo() + "\t" + dto.columnTo();
    }
}
