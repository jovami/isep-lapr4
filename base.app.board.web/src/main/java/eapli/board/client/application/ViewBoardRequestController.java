package eapli.board.client.application;

import static eapli.board.client.ClientServerAjax.HTTP_PORT;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import eapli.board.SBProtocol;
import eapli.board.client.ClientServerAjax;
import jovami.util.exceptions.ReceivedERRCode;
import jovami.util.net.NetTools;

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
        StringBuilder b = new StringBuilder();
        b.append(board);
        b.append("\0");
        b.append(ClientServerAjax.LISTEN_SERVER);

        requestBoard.setContentFromString(b.toString());

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

            // NEW thread that will

            ClientServerAjax.newBoardInfo(dataContent);
            openBrowser("bTitle=" + dataContent[0]);
            sock.close();

        } catch (IOException ex) {
            System.out.println("Error closing socket.");
            System.out.println("Application aborted.");
        } catch (URISyntaxException e) {
            System.out.println("It was not possible to open the browser");
        }
    }

    private void openBrowser(String urlQuery) throws IOException, URISyntaxException {
        String url = String.format("http://localhost:" + HTTP_PORT + "/?" + urlQuery);
        NetTools.browseURL(new URI(url));
    }
}
