package eapli.client.presentation;

import eapli.client.application.ViewBoardRequestController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.List;

public class ViewBoardRequestUI extends AbstractUI {

    private final int serverPort;
    private final InetAddress serverIp;

    public ViewBoardRequestUI(InetAddress serverIP, int serverPort) {
        this.serverIp = serverIP;
        this.serverPort = serverPort;
    }

    @Override
    public boolean doShow() {
        ViewBoardRequestController ctrl;
        try {
            ctrl = new ViewBoardRequestController(serverIp, serverPort);
        } catch (IOException e) {
            System.out.println("Server Busy, try again later!");
            return false;
        }
        try {
            String[] names = ctrl.requestBoards();

            SelectWidget<String> selec = new SelectWidget<>("Choose Board to view\n", List.of(names));
            selec.show();
            if (selec.selectedOption() == 0) {
                return false;
            }

            ctrl.chooseBoard(selec.selectedElement());
        } catch (ReceivedERRCode | IOException | URISyntaxException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    @Override
    public String headline() {
        return "View Boards";
    }
}

