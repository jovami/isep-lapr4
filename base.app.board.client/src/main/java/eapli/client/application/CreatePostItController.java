package eapli.client.application;


import eapli.board.SBProtocol;
import eapli.client.SBPClientApp;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CreatePostItController {
    private Socket sock;
    private DataInputStream inS;
    private DataOutputStream outS;
    private ArrayList<String> boardColumns;


    public CreatePostItController(InetAddress serverIP, int serverPort) throws IOException, ReceivedERRCode {
            this.sock = new Socket(serverIP, serverPort);
            this.inS = new DataInputStream(sock.getInputStream());
            this.outS = new DataOutputStream(sock.getOutputStream());
            canCreatePostIt();
    }


    public boolean isCellIdValid(String arr, int numRows, int numColumns) {
        String[] dimension = arr.split(",");
        if (dimension.length!=2){
            return false;
        }

        int row = Integer.parseInt(dimension[0]);
        int col = Integer.parseInt(dimension[1]);
        return row * col >= 0 && row <= numRows && col <= numColumns;
    }

    public void canCreatePostIt() throws IOException, ReceivedERRCode {
        SBProtocol message = new SBProtocol();
        message.setCode(SBProtocol.CREATE_POST_IT);
        message.setToken(SBPClientApp.authToken());
        message.send(outS);
        SBProtocol received = new SBProtocol(inS);
        String str = received.getContentAsString();
        boardColumns = new ArrayList<>(List.of(str.split(" ")));
    }

    public ArrayList<String> getBoardsList() throws ReceivedERRCode, IOException {
        ArrayList<String> boards = new ArrayList<>();
        for (String s : boardColumns) {
            String[] arr = s.split("\t");
            boards.add(arr[0]);
            boards.add(arr[1]);
            boards.add(arr[2]);
        }
        return boards;
    }



    //TODO: MAYBE ADD THIS TO A SERVICE
    public boolean createPostIt(String str) throws IOException, ReceivedERRCode {
        SBProtocol message = new SBProtocol();
        message.setCode(SBProtocol.SEND_POST_IT_INFO);
        message.setContentFromString(str);
        message.send(outS);
        SBProtocol received = new SBProtocol(inS);
        return received.getCode() == SBProtocol.ACK;

    }

    public StringBuilder createBoardPositionTextString(String boardChosen, String position, String text) {
        StringBuilder sb = new StringBuilder();
        sb.append(boardChosen);
        sb.append("\t");
        sb.append(position);
        sb.append("\t");
        sb.append(text);
        return sb;
    }

    public void close() {
        AuthRequestController.closeSocket(sock);
    }
}
