package eapli.client.application;


import eapli.board.SBProtocol;
import eapli.client.SBPClientApp;
import eapli.framework.application.UseCaseController;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@UseCaseController
public class UpdatePostItController {
    private final Socket sock;
    private final DataInputStream inS;
    private final DataOutputStream outS;

    public UpdatePostItController(InetAddress serverIP, int serverPort) {
        try {
            this.sock = new Socket(serverIP, serverPort);
            this.inS = new DataInputStream(sock.getInputStream());
            this.outS = new DataOutputStream(sock.getOutputStream());
        } catch (IOException | RuntimeException e) {
            System.out.println("Failed to connect to provided SERVER-ADDRESS and SERVER-PORT.");
            System.out.println("Application aborted.");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> listBoardString() throws ReceivedERRCode, IOException {
        var message = new SBProtocol();
        message.setCode(SBProtocol.UPDATE_POST_IT);
        message.setToken(SBPClientApp.authToken());
        message.send(outS);

        var boardColumns = new ArrayList<>(List.of(new SBProtocol(inS).getContentAsString().split(" ")));

        var boards = new ArrayList<String>();
        for (var s : boardColumns) {
            var arr = s.split("\t");
            boards.add(arr[0]);
            boards.add(arr[1]);
            boards.add(arr[2]);
        }

        return boards;
    }

    public boolean updatePostIt(String str) throws IOException, ReceivedERRCode {
        var message = new SBProtocol();
        message.setCode(SBProtocol.UPDATE_POST_IT);
        message.setContentFromString(str);
        message.send(outS);

        var received = new SBProtocol(inS);
        return received.getCode() == SBProtocol.ACK;
    }

    public String createBoardPositionTextString(String boardChosen, String position, String text) {
        return boardChosen + "\t" + position + "\t" + text;
    }

    public void close() {
        AuthRequestController.closeSocket(sock);
    }
}