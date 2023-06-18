package eapli.client;

import eapli.board.SBProtocol;
import eapli.client.application.AJAX.CreatePostItAjax;
import eapli.client.application.AJAX.MovePostItAjax;
import eapli.client.application.AJAX.UpdatePostItAjax;
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

            switch (request.getCode()) {
                case SBProtocol.SEND_POST_IT_INFO:
                    CreatePostItAjax createPostItAjax = new CreatePostItAjax(sock, request);
                    createPostItAjax.run();
                    break;
                case SBProtocol.UPDATE_POST_IT:
                    UpdatePostItAjax updatePostItAjax = new UpdatePostItAjax(sock, request);
                    updatePostItAjax.run();
                    break;
                case SBProtocol.MOVE_POST_IT:
                    MovePostItAjax movePostItAjax = new MovePostItAjax(sock, request);
                    movePostItAjax.run();
                    break;
            }

        } catch (IOException ex) {
            System.out.printf("[WARNING] Thread error when reading request for %s\n",sock.getInetAddress());
        } catch (ReceivedERRCode er) {
            System.out.println("[ERR RECEIVED] " + er.getMessage());
        }

    }
}