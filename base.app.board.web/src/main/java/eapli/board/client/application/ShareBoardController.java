package eapli.board.client.application;

import com.ibm.icu.impl.Pair;
import eapli.base.board.domain.BoardParticipantPermissions;
import eapli.board.SBProtocol;
import eapli.framework.application.UseCaseController;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@UseCaseController
public class ShareBoardController {
    private final List<Pair<String, BoardParticipantPermissions>> usersInvited = new ArrayList<>();
    private ArrayList<String> users = new ArrayList<>();
    private final DataInputStream inS;
    private final DataOutputStream outS;
    private final Socket sock;

    public ShareBoardController(InetAddress serverIp, int serverPort) {
        try {
            sock = new Socket(serverIp, serverPort);
            inS = new DataInputStream(sock.getInputStream());
            outS = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> listBoardsUserOwns() throws IOException, ReceivedERRCode {
        //search for boards that the  user owns
        SBProtocol sendUser = new SBProtocol();
        sendUser.setCode(SBProtocol.GET_BOARDS_OWNED);
        sendUser.send(outS);

        //receive Boards owned
        SBProtocol getBoards = new SBProtocol(inS);

        return List.of(getBoards.getContentAsString().split("\0"));
        //connect with server to get
    }

    public boolean shareBoard() throws IOException, ReceivedERRCode {

        SBProtocol sendInvites = new SBProtocol();

        StringBuilder builder = new StringBuilder();
        for (Pair<String, BoardParticipantPermissions> pair : usersInvited) {
            builder.append(pair.first).append("\\\\").append(pair.second.toString()).append('\0');
        }
        sendInvites.setContentFromString(builder.toString());
        sendInvites.send(outS);

        SBProtocol response = new SBProtocol(inS);

        return response.getCode() == SBProtocol.ACK;
    }

    public List<String> users() throws IOException {
        return users;
    }

    public void prepareInvite(String username, BoardParticipantPermissions perm) {
        usersInvited.add(Pair.of(username, perm));
        users.remove(username);
    }

    public boolean chooseBoard(String boardTitle) throws IOException, ReceivedERRCode {
        //askBoardFromSocket
        SBProtocol chooseBoard = new SBProtocol();
        chooseBoard.setContentFromString(boardTitle);
        chooseBoard.send(outS);

        SBProtocol receiveUsers = new SBProtocol(inS);

        this.users = new ArrayList<>(List.of((receiveUsers.getContentAsString().split("\0"))));
        return true;
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
