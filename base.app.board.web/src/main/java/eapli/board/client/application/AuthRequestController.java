package eapli.board.client.application;

import eapli.board.SBProtocol;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class AuthRequestController {
    private Socket sock;
    private DataInputStream inS;
    private DataOutputStream outS;

    public AuthRequestController(InetAddress serverIP, int serverPort){
        try {
            this.sock = new Socket(serverIP, serverPort);
            this.inS = new DataInputStream(sock.getInputStream());
            this.outS = new DataOutputStream(sock.getOutputStream());
        } catch (IOException| RuntimeException e) {
            System.out.println("Failed to connect to provided SERVER-ADDRESS and SERVER-PORT.");
            System.out.println("Application aborted.");
            System.exit(1);
        }
    }

    public String requestAuth(String username, String password) {
        SBProtocol request = new SBProtocol();

        request.setCode(SBProtocol.AUTH);
        request.setDataLength(username.length() + password.length() + 2);

        String buff = username + "\0" + password + "\0";

        request.setContentFromString(buff);

        //sends request to server
        try {
            request.send(outS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return authStatus();
    }

    private String authStatus() {

        try {
            SBProtocol response = new SBProtocol(inS);
        } catch (IOException e){
            throw new RuntimeException(e);
        }catch (ReceivedERRCode e) {
            return e.getMessage();
        }

        return "ACK";
    }
}
