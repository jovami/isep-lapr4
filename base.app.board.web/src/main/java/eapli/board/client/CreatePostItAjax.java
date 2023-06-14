package eapli.board.client;

import eapli.board.SBProtocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CreatePostItAjax implements Runnable {

    private final SBProtocol createPostitRequest;
    private DataInputStream inS;
    private DataOutputStream outS;
    private final Socket sock;
    public CreatePostItAjax(Socket socket, SBProtocol createPostitRequest) {
        this.sock = socket;
        this.createPostitRequest = createPostitRequest;
    }


    public void run() {
        try {
            inS = new DataInputStream(sock.getInputStream());
            outS = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] createPostitInfo =  createPostitRequest.getContentAsString().split("\t");
        String[] dimensions = createPostitInfo[1].split(",");

        int row =Integer.parseInt(dimensions[0]);
        int col =Integer.parseInt(dimensions[1]);

        ClientServerAjax.addCell(col,row,createPostitInfo[2]);

        /*
        SBProtocol responseStatus = new SBProtocol();
        responseStatus.setCode(SBProtocol.ACK);
        try {
            responseStatus.send(outS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

    }


}
