package eapli.client.application.AJAX;

import eapli.board.SBProtocol;
import eapli.client.application.AuthRequestController;

import java.net.Socket;

public class MovePostItAjax implements Runnable {

    private final SBProtocol protocol;
    private final Socket socket;
    public MovePostItAjax(Socket socket, SBProtocol protocol) {
        this.socket = socket;
        this.protocol = protocol;
    }

    public void run() {
        var postItInfo = protocol.getContentAsString().split("\t");

        int rowFrom = Integer.parseInt(postItInfo[1]);
        int colFrom = Integer.parseInt(postItInfo[2]);
        int rowTo = Integer.parseInt(postItInfo[3]);
        int colTo = Integer.parseInt(postItInfo[4]);

        var dto = ClientServerAjax.getBoardInfo(postItInfo[0]);
        if (dto != null)
            dto.movePostIt(rowFrom, colFrom, rowTo, colTo);

        AuthRequestController.closeSocket(socket);
    }
}
