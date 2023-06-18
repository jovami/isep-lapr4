package eapli.client.application;

import eapli.board.SBProtocol;
import eapli.client.SBPClientApp;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ViewBoardHistoryController {
    private final Socket sock;
    private final DataInputStream inS;
    private final DataOutputStream outS;

    public ViewBoardHistoryController(InetAddress serverIP, int serverPort) throws IOException {

        this.sock = new Socket(serverIP, serverPort);
        this.inS = new DataInputStream(sock.getInputStream());
        this.outS = new DataOutputStream(sock.getOutputStream());
    }

    public String[] requestBoards() throws ReceivedERRCode {
        SBProtocol requestAllBoard = new SBProtocol();
        requestAllBoard.setToken(SBPClientApp.authToken());
        requestAllBoard.setCode(SBProtocol.LIST_HISTORY);

        try {
            requestAllBoard.send(outS);
            SBProtocol receiveBoards = new SBProtocol(inS);
            return receiveBoards.getContentAsString().split("/r");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String[] requestHistory(String board) throws ReceivedERRCode {
        SBProtocol requestHistory = new SBProtocol();
        requestHistory.setCode(SBProtocol.LIST_HISTORY);
        requestHistory.setContentFromString(board);

        try {
            requestHistory.send(outS);

            SBProtocol receivedHistory = new SBProtocol(inS);
            if (receivedHistory.getCode() != SBProtocol.VIEW_BOARD_HISTORY) {
                return null;
            }

            return receivedHistory.getContentAsString().split("\r");

        } catch (IOException ex) {
            return null;
        }
    }
}
