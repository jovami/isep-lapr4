package eapli.client.presentation;

import eapli.client.application.AuthRequestController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jovami.util.exceptions.ReceivedERRCode;
import jovami.util.io.ConsoleUtils;

import java.io.IOException;
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
        int attempts = 0;
        do {

            try {
                ctrl = new AuthRequestController(serverIP, serverPort);
            } catch (IOException e) {
                System.out.println("Server busy, try again later");
                return false;
            }

            String username = Console.readLine("Username: ");
            String password = ConsoleUtils.readPassword("Password: ");

            try {
                attempts++;
                if (ctrl.requestAuth(username, password)) {
                    System.out.println("Logged in Successfully");
                    ctrl.closeSock();
                    return true;
                }
            } catch (ReceivedERRCode e) {
                System.out.println("Log in Failed: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Server busy, try again later");
            } finally {
                ctrl.closeSock();
            }
        } while (attempts < MAX_ATTEMPTS);

        return false;
    }

    @Override
    public String headline() {
        return "SBP - Authentication";
    }
}

