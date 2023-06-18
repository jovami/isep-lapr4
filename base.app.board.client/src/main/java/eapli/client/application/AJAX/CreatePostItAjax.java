package eapli.client.application.AJAX;

import eapli.board.SBProtocol;
import eapli.client.application.AuthRequestController;
import eapli.client.dto.BoardInfoDto;

import java.net.Socket;

public class CreatePostItAjax implements Runnable {

    private final SBProtocol createPostitRequest;
    private final Socket sock;
    public CreatePostItAjax(Socket socket, SBProtocol createPostitRequest) {
        this.sock = socket;
        this.createPostitRequest = createPostitRequest;
    }


    public void run() {
        String[] createPostitInfo =  createPostitRequest.getContentAsString().split("\t");
        String[] dimensions = createPostitInfo[1].split(",");

        int row =Integer.parseInt(dimensions[0]);
        int col =Integer.parseInt(dimensions[1]);

        BoardInfoDto dto = ClientServerAjax.getBoardInfo(createPostitInfo[0]);
        if (dto!=null){
            dto.addPostIt(row,col,createPostitInfo[2]);
        }
        AuthRequestController.closeSocket(sock);
    }
}

