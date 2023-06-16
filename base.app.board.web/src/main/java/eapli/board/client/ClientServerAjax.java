package eapli.board.client;

import eapli.board.HTTPMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ClientServerAjax extends Thread {

    //application properties?
    public static final int HTTP_PORT = 9052;
    //PASS PORT BY PROTOCOL??
    public static final int LISTEN_SERVER = 9070;
    //For each board
    private static final HashMap<String, BoardInfoDto> boardsInfo = new HashMap<>();
    //TODO: UNIX PATH??
    private final String BASE_FOLDER = "base.app.board.web/src/main/java/eapli/board/www";

    public ClientServerAjax() {

    }

    public static BoardInfoDto newBoardInfo(String[] board) {
        BoardInfoDto dto = new BoardInfoDto(board);
        //ADD new BoardInfo
        boardsInfo.putIfAbsent(dto.getTitle(), dto);
        return boardsInfo.get(dto.getTitle());
    }

    //TODO: verify if null
    public static BoardInfoDto getBoardInfo(String s) {
        return boardsInfo.get(s);
    }

    @Override
    public void run() {
        //Read information from the SBServer
        ClientServerChanges cliServ = new ClientServerChanges();
        cliServ.start();

        //test
        //create serverSock
        ServerSocket serverSock;
        try {
            serverSock = new ServerSocket(HTTP_PORT);
        } catch (IOException e) {
            System.out.println("Can't use port %d" + HTTP_PORT + " for the HTTP server, already in use");
            return;
        }

        Socket cliSock;
        while (true) {
            //handle with AJAX each one of the requests
            try {
                cliSock = serverSock.accept();
            } catch (IOException e) {
                System.out.println("Error accepting new Requests");
                break;
            }

            try {


                answerHTTPRequest(cliSock);
            } catch (IOException e) {
                System.out.println("Error while reading socket");

                try {
                    cliSock.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

    }

    private void answerHTTPRequest(Socket cliSock) throws IOException {

        HTTPMessage m = new HTTPMessage(new DataInputStream(cliSock.getInputStream()));
        DataOutputStream outS = new DataOutputStream(cliSock.getOutputStream());
        String boardUri = "/board/";
        String iamgesUri = "/images/";
        if (m.getMethod().equals("GET")) {
            //Get boards
            if (m.getURI().startsWith(boardUri)) {
                String board = m.getURI().substring(boardUri.length());
                String html = generateBoardHtml(board);
                if (html == null) {
                    m.setResponseStatus("Board not Found");
                } else {
                    m.setContentFromString(html, "text/html");
                    m.setResponseStatus("200 OK");
                }
            //get Images
            } else if (m.getURI().startsWith("/images")) {
                m.setContentFromFile(BASE_FOLDER+"/images/" + m.getURI().substring(7));
                m.setResponseStatus("200 OK");
            } else {
                //Base Folder
                m.setContentFromFile(BASE_FOLDER+"/index.html");
                m.setResponseStatus("200 OK");
            }
        }
        m.send(outS);
    }

    private synchronized String generateBoardHtml(String board) {
        int idx =0;
        StringBuilder html = new StringBuilder();

        //GETboard info verify if no exist

        BoardInfoDto dto = boardsInfo.get(board);

        if (dto == null) {
            return null;
        }


        html.append("<tr>\n<td class=\"board-title\">" + dto.getTitle() + "</td>\n");
        for (int i = 0; i < dto.getNumCols(); i++) {
            html.append("<td class=\"headers\">" + (i + 1) + "</td>\n");
        }
        html.append("</tr>\n");


        for (int i = 0; i < dto.getNumRows(); i++) {
            html.append(String.format("<tr>\n"));
            html.append("<td class=\"headers\">" + (i + 1) + "</td>");
            for (int j = 0; j < dto.getNumCols(); j++) {
                String content = dto.getDataContent()[idx];

                if (content.equals(" ")) {
                    html.append(String.format("<td id=\"/row%d/col%d\">\n", i, j));//style='background-color:yellow'
                    //THERE IS NO CONTENT
                } else if (content.startsWith("\"") && content.endsWith("\"")){


                    //properly parse images
                    content = content.replaceAll("\"", "");
                    String image = "images";
                    content = content.substring(content.lastIndexOf(image));

                    StringBuilder b = new StringBuilder();
                    b.append(image);
                    b.append(content.substring(content.lastIndexOf(image) + image.length()));

                    html.append(String.format("<td><div class=\"postIt\"><img src='%s'class =\"cell-img\"></img></div>\n", b));

                    //CELL CONTENT
                } else {
                    html.append(String.format("<td><div class=\"postIt\">%s</div>\n", dto.getDataContent()[idx]));
                }
                idx++;
                html.append("</td>\n");
            }
            html.append("</tr>\n");
        }


        return html.toString();
    }


}

