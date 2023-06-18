package eapli.client.application.AJAX;

import eapli.board.SBProtocol;
import eapli.client.application.AuthRequestController;

import java.net.Socket;

public class UpdatePostItAjax implements Runnable {

    private final SBProtocol protocol;
    private final Socket socket;
    public UpdatePostItAjax(Socket socket, SBProtocol protocol) {
        this.socket = socket;
        this.protocol = protocol;
    }

    public void run() {
        var postItInfo =  protocol.getContentAsString().split("\t");
        var dimensions = postItInfo[1].split(",");

        int row = Integer.parseInt(dimensions[0]);
        int col = Integer.parseInt(dimensions[1]);

        var dto = ClientServerAjax.getBoardInfo(postItInfo[0]);
        if (dto!=null)
            dto.addPostIt(row,col,postItInfo[2]);

        AuthRequestController.closeSocket(socket);
    }
}
