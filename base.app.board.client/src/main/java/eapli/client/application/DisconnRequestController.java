package eapli.client.application;

import eapli.board.SBProtocol;
import eapli.client.SBPClientApp;
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

    public DisconnRequestController(InetAddress serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public boolean disconn() {
        boolean disconnected = false;
        do {
            try {
                sock = new Socket(serverIP, serverPort);
                inS = new DataInputStream(sock.getInputStream());
                outS = new DataOutputStream(sock.getOutputStream());

                SBProtocol request = new SBProtocol();
                request.setCode(SBProtocol.DISCONN);
                request.setToken(SBPClientApp.authToken());
                //sends request to server
                request.send(outS);
                //reads server response
                SBProtocol response = new SBProtocol(inS);

                //VERIFY IF THE DISCONN WAS SUCCESSFUL
                disconnected = response.getCode() == SBProtocol.ACK;

                try {
                    sock.close();
                } catch (IOException ex2) {
                    System.out.println("Error closing socket.");
                    System.out.println("Application aborted.");
                }

            } catch (IOException | ReceivedERRCode e) {
            }
        } while (!disconnected);

        return disconnected;
    }
}
