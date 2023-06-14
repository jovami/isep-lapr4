package eapli.board.client.application;

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

    //TODO: is this a service??
    public CommTestController(InetAddress serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public boolean commTest() {

        SBProtocol response;
        try {
            sock = new Socket(serverIP, serverPort);
            inS = new DataInputStream(sock.getInputStream());
            outS = new DataOutputStream(sock.getOutputStream());

            SBProtocol request = new SBProtocol();
            request.setCode(SBProtocol.COMMTEST);
            request.send(outS);

            response = new SBProtocol(inS);

        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
                return false;
            }
        }
        return response.getCode() == SBProtocol.ACK;
    }
}
