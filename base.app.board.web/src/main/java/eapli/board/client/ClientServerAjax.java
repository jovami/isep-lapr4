package eapli.board.client;

import eapli.board.HTTPMessage;
import org.apache.commons.lang3.SystemUtils;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class ClientServerAjax extends Thread {
    private final int HEADER_SIZE = 3;
    public static final int HTTP_PORT = 7000;
    //PASS PORT BY PROTOCOL??
    public static final int LISTEN_SERVER = 7010;
    //For each board
    private static String[] dataContent;
    private final int cols;
    private final String title;
    private final int rows;

    public ClientServerAjax(String[] board) {
        dataContent = board;
        this.title = dataContent[0];
        this.rows = Integer.parseInt(dataContent[1]);
        this.cols = Integer.parseInt(dataContent[2]);
    }

    @Override
    public void run() {
        ClientServerChanges cliServ = new ClientServerChanges(dataContent);
        cliServ.start();
        try {
            //test

            String html = generateBoardHtml();

            //create serverSock
            ServerSocket serverSock = new ServerSocket(HTTP_PORT);
            openBrowser();


            Socket cliSock;
            //setHttpConection(html, cliSock);
            int i = 0;
            while (true) {
                //handle with AJAX each one of the requests
                cliSock = serverSock.accept();

                HTTPMessage m = new HTTPMessage(new DataInputStream(cliSock.getInputStream()));

                DataOutputStream outS = new DataOutputStream(cliSock.getOutputStream());

                if (m.getMethod().equals("GET")) {
                    if (m.getURI().startsWith("/board")) {
                        //handle statically
                        m.setContentFromString(generateBoardHtml(), "text/html");
                        m.setResponseStatus("200 OK");
                    }else {
                        m.setContentFromFile("base.app.board.web\\src\\main\\java\\eapli\\board\\www\\index.html");
                        System.out.println("es boi");
                        m.setResponseStatus("200 OK");
                    }
                }
                m.send(outS);
                i++;
            }

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void addPostIt(int i) {
        dataContent[3+i] = ("post-it: "+i);
    }

    private static void setHttpConection(String html, Socket cliSock) throws IOException {
        DataInputStream insS = new DataInputStream(cliSock.getInputStream());
        DataOutputStream outS = new DataOutputStream(cliSock.getOutputStream());

        HTTPMessage mess = new HTTPMessage();
        mess.setContentFromString(html, "text/html");
        mess.setResponseStatus("200 OK");
        mess.send(outS);

        cliSock.close();
    }

    private static void openBrowser() throws IOException, URISyntaxException {
        Desktop d = Desktop.getDesktop();

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            d.browse(new URI("http://localhost:" + HTTP_PORT));
        } else {
            String os = SystemUtils.OS_NAME.toLowerCase();
            if (os.contains("win")) {
                Runtime rt = Runtime.getRuntime();
                rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:7000");
            } else if (os.contains("mac")) {
                Runtime rt = Runtime.getRuntime();
                rt.exec("open " + "http://localhost:7000");
            } else if (os.contains("nux")) {
                Runtime rt = Runtime.getRuntime();
                rt.exec("xdg-open " + "http://localhost:7000");
            }
        }
    }

    private String generateBoardHtml() {
        int idx = HEADER_SIZE;
        StringBuilder html = new StringBuilder();

        /*html.append("<!DOCTYPE html>\n");
        //CSS
        html.append(getStyles());

        //TODO
        //html.append("<body onload=\"refreshBoard("+title+")/>");
        html.append("<body onload=\"refreshBoard()\"/>");

        //set table URI
        //html.append("<table id = \"board\\"+title+"\">");
        html.append("<table id = \"board\">");*/
        //html.append("<tr>\n<td class=\"board-title\">" + title + "</td>\n");
        html.append("<tr>\n<td class=\"board-title\">" + title + "</td>\n");
        for (int i = 0; i < cols; i++) {
            html.append("<td class=\"headers\">" + (i + 1) + "</td>\n");
        }
        html.append("</tr>\n");

        for (int i = 0; i < rows; i++) {
            html.append(String.format("<tr>\n"));
            html.append("<td class=\"headers\">" + (i + 1) + "</td>");
            for (int j = 0; j < cols; j++) {
                String content = dataContent[idx];

                if (content.equals(" ")) {
                    html.append(String.format("<td id=\"/row%d/col%d\">\n", i, j));//style='background-color:yellow'
                    //THERE IS NO CONTENT
                } else if (content.startsWith("\"") && content.endsWith("\"")){
                    html.append(String.format("<td id=\"/row%d/col%d\"><div class=\"postIt\"><img src=%s ></img></div>\n", i, j, dataContent[idx]));
                    //CELL CONTENT
                }else {
                    html.append(String.format("<td id=\"/row%d/col%d\"><div class=\"postIt\">%s</div>\n", i, j, dataContent[idx]));
                }
                idx++;
                html.append("</td>\n");
            }
            html.append("</tr>\n");
        }
        /*html.append("</table>\n");
        html.append("</body");
        html.append("</html>");*/

        return html.toString();
    }




    private String getStyles() {
        return "<style>\n" +
                getBodyStyle() +
                getTdStyle() +
                getTableStyle() +
                getImgStyle() +
                getTitleStyle() +
                getHeaderStyle() +
                getPostItStyle() +
                "</style>\n";
    }

    private String getPostItStyle() {
        return ".postIt {\n" +
                "        margin: 5px 5px 5px 5px;\n" +
                "        background: grey;\n" +
                "        word-wrap: break-word;\n" +
                "        border-radius: 5px\n" +
                "\n" +
                "}";
    }

    private String getHeaderStyle() {
        return ".headers {\n" +
                "    font-size: 20px;\n" +
                "    text-align: center;\n" +
                "    padding: 10px;\n" +
                "    background-color: #E6E6E4;\n" +
                "}";
    }


    public String getAjaxSwapCells() {
        return "<script>\n" +
                "function swapCells(cel1,cel2) {\n" +
                "  var xhttp = new XMLHttpRequest();\n" +
                "  xhttp.onreadystatechange = function() {\n" +
                "    if (this.readyState == 4 && this.status == 200) {\n" +
                "      document.getElementById(\"cel1\").innerHTML =\n" +
                "      document.getElementById(\"cel2\").innerHTML =\n" +
                "    }\n" +
                "  };\n" +
                "  xhttp.send();\n" +
                "}\n" +
                "</script>";
    }

    private static String getImgStyle() {
        return ("body {" +
                "\tmax-width: 100%;\n" +
                "\tmax-height: 100px;\n" +
                "}\n");
    }

    private static String getBodyStyle() {
        return ("body {" +
                "\tfont-family: \"Times New Roman\", sans-serif;\n" +
                "\tbackground-color: #f2f2f2;\n" +
                //"\tbackground-image: url("space.png");\n" +
                "\tbackground-repeat: no-repeat;\n" +
                "\tbackground-attachment: fixed;\n" +
                "\tbackground-position: center top;\n" +
                "\tbackground-size: cover;\n" +
                "}\n");
    }

    private static String getTdStyle() {
        return ("td{" +
                "\tpadding: 10px;\n" +
                "\ttext-align: center;\n" +
                "\tborder: 1px solid #dddddd;\n" +
                "\tword-wrap: break-word;\n" +
                "}\n");
    }

    private static String getTableStyle() {
        return ("table{" +
                "\tborder: dark-blue;\n" +
                "\tborder-collapse: collapse;\n" +
                "\tmargin: 2% auto;\n" +
                "\tbackground-color: #ffffff;\n" +
                "\tbox-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "\ttable-layout: fixed;\n" +
                "\twidth: 80%;\n" +
                "}\n");
    }

    private static String getTitleStyle() {
        return ".board-title {\n" +
                "    font-size: 25px;\n" +
                "    font-weight: bold;\n" +
                "    text-align: center;\n" +
                "    padding: 10px;\n" +
                "    background-color: #E6E6FA;\n" +
                "}";
    }

    public static void setDataContent(String[] dataContent) {
        ClientServerAjax.dataContent = dataContent;
    }
    public static void addCell(int col, int row, String info) {
        dataContent[3+(col*row)-1] = info;
    }
}

