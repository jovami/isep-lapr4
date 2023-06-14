package eapli.board.client;

import eapli.board.SBProtocol;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class MenuChanges extends Thread {
    // private String baseFolder;
    private final Socket sock;

    public MenuChanges(Socket s) {
        sock = s;
    }

    public void run() {
        try {
            DataInputStream inS = new DataInputStream(sock.getInputStream());

            SBProtocol request = new SBProtocol(inS);

            //depending on the SBPMessageCode

            System.out.print("Menu_Changes:");
            switch (request.getCode()) {
                case SBProtocol.SEND_POST_IT_INFO:
                    System.out.println("SEND_POST_IT_INFO");
                    CreatePostItAjax createPostItAjax = new CreatePostItAjax(sock, request);
                    createPostItAjax.run();
                    break;
            }

        } catch (IOException ex) {
            System.out.printf("[WARNING] Thread error when reading request for %s",sock.getInetAddress());
        } catch (ReceivedERRCode er) {
            System.out.println("[ERR RECEIVED] " + er.getMessage());
        }

    }
}