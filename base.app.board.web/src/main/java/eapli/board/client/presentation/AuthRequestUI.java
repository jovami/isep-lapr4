package eapli.board.client.presentation;

import eapli.board.client.application.AuthRequestController;
import eapli.board.client.SBPClientApp;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jovami.util.io.ConsoleUtils;

import java.net.InetAddress;


public class AuthRequestUI extends AbstractUI {
    private final int serverPort;
    private final InetAddress serverIP;
    private final int MAX_ATTEMPTS = 3;

    public AuthRequestUI(InetAddress address, int serverPort) {
        this.serverIP = address;
        this.serverPort = serverPort;
    }

    @Override
    protected boolean doShow() {
        AuthRequestController ctrl;
        try {
            int attempts = 0;
            do {
                 ctrl = new AuthRequestController(serverIP, serverPort);

                String username = Console.readLine("Username: ");
                String password = ConsoleUtils.readPassword("Password: ");

                String status = ctrl.requestAuth(username, password);

                if (status.equals("ACK")) {
                    System.out.println("Logged in Successfully");
                    SBPClientApp.setUsername(username);
                    return true;
                } else {
                    attempts++;
                    System.out.println("Log in Failed: " + status);
                }
            } while (attempts < MAX_ATTEMPTS);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        };
        return false;
    }

    @Override
    public String headline() {
        return "SBP - Authentication";
    }
}

