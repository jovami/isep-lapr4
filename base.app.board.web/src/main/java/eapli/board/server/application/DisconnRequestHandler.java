package eapli.board.server.application;

import eapli.board.SBProtocol;
import eapli.board.server.SBPServerApp;
import eapli.board.server.domain.Client;
import eapli.framework.validations.Preconditions;

import java.io.IOException;
import java.net.Socket;

public class DisconnRequestHandler extends AbstractSBServerHandler {
    public DisconnRequestHandler(Socket sock, SBProtocol request) {
        super(sock,request);
        Preconditions.areEqual(request.getCode(), SBProtocol.DISCONN);
    }

    public void run() {
        if (this.request.getCode()!= SBProtocol.DISCONN){
            System.out.println("Code message should be "+ SBProtocol.DISCONN +" to disconnect the board");
            return;
        }

        SBProtocol responseSent = new SBProtocol();
        Client user = SBPServerApp.activeAuths.remove(sock.getInetAddress());
        System.out.printf("[DISCONN] User: %s\tIp: %s \n",
                user.getUserLoggedIn().username(), sock.getInetAddress().toString());

        responseSent.setCode(SBProtocol.ACK);
        try {
            responseSent.send(outS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
