package eapli.board.client.application;


import eapli.board.SBProtocol;
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


    public CreatePostItController(InetAddress serverIP, int serverPort) {
        try {
            this.sock = new Socket(serverIP, serverPort);
            this.inS = new DataInputStream(sock.getInputStream());
            this.outS = new DataOutputStream(sock.getOutputStream());
            canCreatePostIt();
        } catch (IOException | RuntimeException e) {
            System.out.println("Failed to connect to provided SERVER-ADDRESS and SERVER-PORT.");
            System.out.println("Application aborted.");
            throw new RuntimeException(e);
        } catch (ReceivedERRCode e) {
            throw new RuntimeException(e);
        }
    }


    public boolean isCellIdValid(String arr, int numRows, int numColumns) {
        String[] dimension = arr.split(",");
        int row = Integer.parseInt(dimension[0]);
        int col = Integer.parseInt(dimension[1]);
        return row * col >= 0 && row <= numRows && col <= numColumns;
    }

    public void canCreatePostIt() throws IOException, ReceivedERRCode {
        SBProtocol message = new SBProtocol();
        message.setCode(SBProtocol.CREATE_POST_IT);
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

    public ArrayList<String> getRowsColumnsList() throws ReceivedERRCode, IOException {
        ArrayList<String> rowsColumns = new ArrayList<>();
        for (String s : boardColumns) {
            String[] arr = s.split("\t");
            rowsColumns.add(arr[1]);
            rowsColumns.add(arr[2]);
        }
        return rowsColumns;
    }


    //TODO: MAYBE ADD THIS TO A SERVICE
    public String createPostIt(String str) throws IOException, ReceivedERRCode {
        SBProtocol message = new SBProtocol();
        message.setCode(SBProtocol.SEND_POST_IT_INFO);
        message.setContentFromString(str);
        message.send(outS);
        SBProtocol received = new SBProtocol(inS);
        if (received.getCode() == SBProtocol.ACK) {
            sock.close();

            return "Post-it created successfully.";
            //TODO: REMOVE ELSE AND DO A FINALLY TO CLOSE THE SOCKET ON THE UI
        } else {
            sock.close();
            return null;
        }

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
}
