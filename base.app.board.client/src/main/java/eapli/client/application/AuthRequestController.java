package eapli.client.application;

import eapli.board.SBProtocol;
import eapli.client.SBPClientApp;
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

    public AuthRequestController(InetAddress serverIP, int serverPort) throws IOException {
        this.sock = new Socket(serverIP, serverPort);
        this.inS = new DataInputStream(sock.getInputStream());
        this.outS = new DataOutputStream(sock.getOutputStream());

    }

    public boolean requestAuth(String username, String password) throws ReceivedERRCode, IOException {
        SBProtocol request = new SBProtocol();

        request.setCode(SBProtocol.AUTH);
        String buff = username + "\0" + password + "\0";
        request.setContentFromString(buff);

        //sends request to server
        request.send(outS);

        return authStatus();
    }

    private boolean authStatus() throws ReceivedERRCode, IOException {

        SBProtocol response = new SBProtocol(inS);
        SBPClientApp.setToken(response.getContentAsString());
        return response.getCode() != SBProtocol.ERR;

    }

    public void closeSock() {
        closeSocket(this.sock);
    }

    public static void closeSocket(Socket socket) {

        try {
            socket.close();
        } catch (IOException ignored) {
        }

    }
}
