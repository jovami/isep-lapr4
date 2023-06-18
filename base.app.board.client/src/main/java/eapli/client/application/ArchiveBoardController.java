package eapli.client.application;


import eapli.board.SBProtocol;
import eapli.client.SBPClientApp;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class ArchiveBoardController {

    private final DataInputStream inS;
    private final DataOutputStream outS;
    private final Socket sock;

    public ArchiveBoardController(InetAddress serverIp, int serverPort) throws IOException {
            sock = new Socket(serverIp, serverPort);
            inS = new DataInputStream(sock.getInputStream());
            outS = new DataOutputStream(sock.getOutputStream());

    }

    public List<String> listBoardsUserOwnsNotArchived() throws IOException, ReceivedERRCode {
        //search for boards that the  user owns and aren't archived
        SBProtocol sendUser = new SBProtocol();
        sendUser.setCode(SBProtocol.GET_BOARDS_OWNED_NOT_ARCHIVED);
        sendUser.setToken(SBPClientApp.authToken());
        sendUser.send(outS);

        //receive Boards owned
        SBProtocol getBoards = new SBProtocol(inS);

        return List.of(getBoards.getContentAsString().split("\0"));
        //connect with server to get
    }

    public String[] archivedBoards(String boardTitle) throws IOException, ReceivedERRCode {
        //askBoardFromSocket
        SBProtocol chooseBoard = new SBProtocol();
        chooseBoard.setContentFromString(boardTitle);

        chooseBoard.send(outS);

        SBProtocol getBoards = new SBProtocol(inS);

        return getBoards.getContentAsString().split("\0");
    }

    public void closeSocket()
    {
        try{
            sock.close();
        } catch (IOException e) {
        }
    }


}
