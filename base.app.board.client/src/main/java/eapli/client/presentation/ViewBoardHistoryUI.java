package eapli.client.presentation;

import eapli.client.application.ViewBoardHistoryController;
import eapli.base.board.dto.BoardHistoryMapper;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class ViewBoardHistoryUI extends AbstractUI {
    private final int serverPort;
    private final InetAddress serverIp;
    private ViewBoardHistoryController controller;
    private final BoardHistoryMapper mapper = new BoardHistoryMapper();


    public ViewBoardHistoryUI(InetAddress serverIP, int serverPort) {
        this.serverIp = serverIP;
        this.serverPort = serverPort;
    }

    @Override
    public boolean doShow() {
        try {
            controller = new ViewBoardHistoryController(serverIp, serverPort);
        } catch (IOException e) {
            System.out.println("Server Busy, try again later!");
            return false;
        }
        try {
            String[] names = controller.requestBoards();

            SelectWidget<String> selec = new SelectWidget<>("Choose Board to view\n", List.of(names));
            selec.show();
            if (selec.selectedOption() == 0) {
                return false;
            }
            String[] history = controller.requestHistory(selec.selectedElement());
            if (history == null) {
                System.out.println("Error receiving history");
                return false;
            }

            if (history.length == 0 || history[0].equals("")){
                System.out.println("No history to show");
                return false;
            }
            System.out.println(header());
            for (String s : history) {
                System.out.println(mapper.toDTO(s));
            }


        } catch (ReceivedERRCode e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String header() {
        return String.format("  %-6s | %-10s |  %-5s  |      %-13s  | %-20s | %s",
                "Type", "Title", "Cell", "Alter Time", "Previous Text", "New Text");
    }
    @Override
    public String headline() {
        return "View History";
    }
}
