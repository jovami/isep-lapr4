package eapli.server.application.newChangeEvent;

import eapli.board.MessageProtocol;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendMessageThread extends Thread {

    private final Socket sock;
    private MessageProtocol message;

    public SendMessageThread(Socket sock, MessageProtocol send){
        this.message = send;
        this.sock = sock;
    }

    @Override
    public void run() {
        try {
            DataOutputStream outS = new DataOutputStream(sock.getOutputStream());
            message.send(outS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
