package eapli.board.server.application;

import eapli.board.SBPMessage;
import eapli.board.server.MenuRequest;
import eapli.framework.validations.Preconditions;
import eapli.board.server.domain.Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DisconnRequestHandler implements Runnable{
    private final Socket sock;
    private DataOutputStream outS;
    private SBPMessage request;

    public DisconnRequestHandler(Socket sock,SBPMessage request) {
        Preconditions.areEqual(request.getCode(),SBPMessage.DISCONN);
        this.request = request;
        this.sock = sock;
    }

    public void run() {
        if (this.request.getCode()!=SBPMessage.DISCONN){
            System.out.println("Code message should be "+ SBPMessage.DISCONN +" to disconnect the board");
            return;
        }
        try {
            outS = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SBPMessage responseSent = new SBPMessage();
        Client user = MenuRequest.remove(sock.getInetAddress());
        System.out.printf("[DISCONN] User: %s\tIp: %s \n",
                user.getUserLoggedIn().username(), sock.getInetAddress().toString());

        responseSent.setCode(SBPMessage.ACK);
        try {
            responseSent.send(outS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
