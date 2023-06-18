package eapli.client.application;

import eapli.board.SBProtocol;
import eapli.client.application.AJAX.ClientServerAjax;
import eapli.client.SBPClientApp;
import jovami.util.exceptions.ReceivedERRCode;
import jovami.util.net.NetTools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import static eapli.client.application.AJAX.ClientServerAjax.HTTP_PORT;

public class ViewBoardRequestController {
    private final Socket sock;
    private final DataInputStream inS;
    private final DataOutputStream outS;
    private final int HEADER_SIZE = 3;

    public ViewBoardRequestController(InetAddress serverIP, int serverPort) throws IOException {

        this.sock = new Socket(serverIP, serverPort);
        this.inS = new DataInputStream(sock.getInputStream());
        this.outS = new DataOutputStream(sock.getOutputStream());
    }

    public String[] requestBoards() throws ReceivedERRCode, IOException {
        SBProtocol requestAllBoard = new SBProtocol();
        requestAllBoard.setToken(SBPClientApp.authToken());
        requestAllBoard.setCode(SBProtocol.VIEW_ALL_BOARDS);


        requestAllBoard.send(outS);
        SBProtocol receiveBoards = new SBProtocol(inS);
        return receiveBoards.getContentAsString().split("\0");
    }

    public void chooseBoard(String board) throws ReceivedERRCode, IOException, URISyntaxException {
        SBProtocol requestBoard = new SBProtocol();
        requestBoard.setCode(SBProtocol.CHOOSE_BOARD);

        StringBuilder b = new StringBuilder();
        b.append(board);
        b.append("\0");
        b.append(ClientServerAjax.LISTEN_SERVER);

        requestBoard.setContentFromString(b.toString());

        requestBoard.send(outS);

        SBProtocol receiveHtml = new SBProtocol(inS);

        //board\0col\0row\0<cells info>
        String d = receiveHtml.getContentAsString();

        String[] dataContent = d.split("\0");


        ClientServerAjax.newBoardInfo(dataContent);
        //setup request for boards on browser
        openBrowser("bTitle=" + dataContent[0]);
        sock.close();
    }

    private void openBrowser(String urlQuery) throws IOException, URISyntaxException {
        String url = String.format("http://localhost:" + HTTP_PORT + "/?" + urlQuery);
        NetTools.browseURL(new URI(url));
    }
}
