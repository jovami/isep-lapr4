package eapli.board.client.application;

import eapli.board.SBProtocol;
import eapli.board.client.ClientServerAjax;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ViewBoardRequestController {
    private final Socket sock;
    private final DataInputStream inS;
    private final DataOutputStream outS;
    private final int HEADER_SIZE = 3;

    public ViewBoardRequestController(InetAddress serverIP, int serverPort) {
        try {
            this.sock = new Socket(serverIP, serverPort);
            this.inS = new DataInputStream(sock.getInputStream());
            this.outS = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] requestBoards() throws ReceivedERRCode {
        SBProtocol requestAllBoard = new SBProtocol();
        requestAllBoard.setCode(SBProtocol.VIEW_ALL_BOARDS);

        try {
            requestAllBoard.send(outS);
            SBProtocol receiveBoards = new SBProtocol(inS);
            return receiveBoards.getContentAsString().split("\0");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void chooseBoard(String board) throws ReceivedERRCode {
        SBProtocol requestBoard = new SBProtocol();
        requestBoard.setCode(SBProtocol.CHOOSE_BOARD);
        requestBoard.setContentFromString(board);

        try {
            requestBoard.send(outS);

            SBProtocol receiveHtml = new SBProtocol(inS);
            if (receiveHtml.getCode() != SBProtocol.GET_BOARD) {
                System.out.println("there was an error");
                return;
            }

            String[] dataContent = receiveHtml.getContentAsString().split("\0");

            int rows = Integer.parseInt(dataContent[1]);
            int cols = Integer.parseInt(dataContent[2]);

            if ((rows * cols) != dataContent.length - HEADER_SIZE) {
                System.out.println("[WARNING] Data was corrupted when asking for board information");
            }

            //NEW thread that will

            ClientServerAjax cliServAjax = new ClientServerAjax(dataContent);
            cliServAjax.start();

            sock.close();

        } catch (IOException ex) {
            System.out.println("Error closing socket.");
            System.out.println("Application aborted.");
        }
    }
}