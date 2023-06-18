package eapli.client.application;

import eapli.board.SBProtocol;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class CommTestController {
    private final int serverPort;
    private final InetAddress serverIP;
    private DataInputStream inS;
    private DataOutputStream outS;
    private Socket sock;

    public CommTestController(InetAddress serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public boolean commTest() throws ReceivedERRCode {

        SBProtocol response;
        try {
            sock = new Socket(serverIP, serverPort);
            inS = new DataInputStream(sock.getInputStream());
            outS = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Server not available, try again later");
        }

        SBProtocol request = new SBProtocol();
        request.setCode(SBProtocol.COMMTEST);
        try {
            request.send(outS);
            response = new SBProtocol(inS);
        } catch (IOException e) {
            throw new RuntimeException("Server not available, try again later");
        }

        AuthRequestController.closeSocket(sock);

        return response.getCode() == SBProtocol.ACK;
    }
}
