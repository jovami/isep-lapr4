package eapli.client.application;

import eapli.board.SBProtocol;
import eapli.client.SBPClientApp;
import eapli.client.dto.NotificationDto;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NotificationsController {
    private final DataInputStream inS;
    private final DataOutputStream outS;
    private final Socket sock;

    public NotificationsController(InetAddress serverIP, int serverPort) throws IOException {

        sock = new Socket(serverIP, serverPort);
        inS = new DataInputStream(sock.getInputStream());
        outS = new DataOutputStream(sock.getOutputStream());

    }

    public List<NotificationDto> listNotfs() throws IOException, ReceivedERRCode {
        //search for boards that the  user owns
        SBProtocol sendUser = new SBProtocol();
        sendUser.setCode(SBProtocol.VIEW_NOTFS);
        sendUser.setToken(SBPClientApp.authToken());
        sendUser.send(outS);

        //receive Boards owned
        SBProtocol getNotfs = new SBProtocol(inS);
        List<NotificationDto> dto = new ArrayList<>();

        for (String str : getNotfs.getContentAsString().split("\0")) {
            dto.add(new NotificationDto(str.split("#&&#")));
        }
        return dto;

    }

    public void sockClose() {
        do {
            try {
                sock.close();
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (true);
    }
}
