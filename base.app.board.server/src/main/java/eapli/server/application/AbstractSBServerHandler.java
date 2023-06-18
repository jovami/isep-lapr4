package eapli.server.application;

import eapli.board.SBProtocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class AbstractSBServerHandler implements Runnable {

    protected Socket sock;
    protected SBProtocol request;
    protected DataInputStream inS;
    protected DataOutputStream outS;
    protected String authToken;

    public AbstractSBServerHandler(Socket sock, SBProtocol protocol){
        this.sock = sock;
        this.request = protocol;
        try {
            this.outS = new DataOutputStream(sock.getOutputStream());
            this.inS = new DataInputStream(sock.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authToken=request.token();
    }
}
