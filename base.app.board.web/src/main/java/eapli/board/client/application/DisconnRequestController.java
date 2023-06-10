package eapli.board.client.application;

import eapli.board.SBPMessage;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class DisconnRequestController {
    private final int serverPort;
    private final InetAddress serverIP;
    private DataInputStream inS;
    private DataOutputStream outS;
    private Socket sock;

    //TODO: is this a service??
    public DisconnRequestController(InetAddress serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public boolean disconn() {
        boolean disconnected;

        do {

            try {
                sock = new Socket(serverIP, serverPort);
                inS = new DataInputStream(sock.getInputStream());
                outS = new DataOutputStream(sock.getOutputStream());

                SBPMessage request = new SBPMessage();
                request.setCode(SBPMessage.DISCONN);
                //sends request to server
                request.send(outS);
                //reads server response
                SBPMessage response = new SBPMessage(inS);

                //VERIFY IF THE DISCONN WAS SUCCESSFUL
                disconnected = response.getCode() == SBPMessage.ACK;

                try {
                    sock.close();
                } catch (IOException ex2) {
                    System.out.println("Error closing socket.");
                    System.out.println("Application aborted.");
                }

            } catch (IOException | ReceivedERRCode e) {
                throw new RuntimeException(e);
            }
        } while (!disconnected);

        return disconnected;
    }
}
