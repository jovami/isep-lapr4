package eapli.client.presentation;

import eapli.client.application.NotificationsController;
import eapli.client.dto.NotificationDto;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListWidget;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class NotificationsUI extends AbstractUI {

    private final int serverPort;
    private final InetAddress serverIP;
    private final List<NotificationDto> readNotifications = new ArrayList<>();


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
            if (notfs.isEmpty()) {
                System.out.println("No notifications to show");
                return false;
            }

            List<NotificationDto> unreadNotfs = getUnreadNotifications(notfs);

            if (unreadNotfs.isEmpty()) {
                System.out.println("No notifications to show");
                return false;
            }

            ListWidget<NotificationDto> listNotfs = new ListWidget<>("Notifications: ", unreadNotfs);
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

    private List<NotificationDto> getUnreadNotifications(List<NotificationDto> allNotifications) {
        List<NotificationDto> unreadNotifications = new ArrayList<>();
        for (NotificationDto notification : allNotifications) {
            if (!readNotifications.contains(notification)) {
                unreadNotifications.add(notification);
            }
        }
        return unreadNotifications;
    }


    @Override
    public String headline() {
        return "Notifications";
    }

}
