package eapli.client.presentation;

import eapli.client.application.NotificationsController;
import eapli.client.dto.NotificationDto;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListWidget;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class NotificationsUI extends AbstractUI {

    private final int serverPort;
    private final InetAddress serverIP;

    public NotificationsUI(InetAddress serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    @Override
    protected boolean doShow() {
        NotificationsController controller;
        try {
            controller = new NotificationsController(serverIP, serverPort);
        } catch (IOException e) {
            System.out.println("Server is busy, try again later");
            return false;
        }

        try {
            List<NotificationDto> notfs = controller.listNotfs();

            ListWidget<NotificationDto> listNotfs = new ListWidget<>("Notifications: ", notfs);
            listNotfs.show();

        } catch (IOException e) {
            System.out.println("Error while reading data from socket");
        } catch (ReceivedERRCode e) {
            System.out.println(e.getMessage());
            return false;
        } finally {

            controller.sockClose();
        }
        return false;
    }

    @Override
    public String headline() {
        return "Notifications";
    }

}
