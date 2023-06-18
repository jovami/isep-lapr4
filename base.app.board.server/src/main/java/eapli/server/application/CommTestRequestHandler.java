package eapli.server.application;

import eapli.board.SBProtocol;
import eapli.framework.validations.Preconditions;

import java.io.IOException;
import java.net.Socket;

public class CommTestRequestHandler extends AbstractSBServerHandler {

    public CommTestRequestHandler(Socket sock, SBProtocol request) {
        super(sock, request);
        Preconditions.areEqual(request.getCode(), SBProtocol.COMMTEST);
    }

    public void run() {
        SBProtocol sent = new SBProtocol();
        if (this.request.getCode() != SBProtocol.COMMTEST) {
            sent.setCode(SBProtocol.ERR);
        } else {
            sent.setCode(SBProtocol.ACK);
        }
        try {
            sent.send(outS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
