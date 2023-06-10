package eapli.board.client;

import eapli.board.client.presentation.AuthRequestUI;
import eapli.board.client.application.DisconnRequestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SBPClientApp {
    static private InetAddress serverIP;
    static private int serverPort;
    private static final String SEPARATOR_LABEL = "------------------------------";
    public static String username;


    public static void main(String[] args) throws Exception {
        parseArgs(args);

        //Before any action, the user is forced to login
        AuthRequestUI auth = new AuthRequestUI(serverIP, serverPort);
        if (!auth.show()) {
            //if login failed terminate app
            endMessage();
            return;
        }

        //DO WORK
        try {
            MainMenu menu = new MainMenu(serverIP, serverPort);
            menu.mainLoop();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

    } // MAIN METHOD

    private static void disconnect() {
        DisconnRequestController ctrl = new DisconnRequestController(serverIP, serverPort);

        if (ctrl.disconn()) {
            System.out.println(SEPARATOR_LABEL);
            System.out.println(SEPARATOR_LABEL + "\nDisconnected Successfully from SBP server\n");
            System.out.println(SEPARATOR_LABEL);
            System.out.println(SEPARATOR_LABEL);
        } else {
            System.out.println(SEPARATOR_LABEL);
            System.out.println(SEPARATOR_LABEL + "\nUnsuccessfully Disconnected from SBP server\n");
            System.out.println(SEPARATOR_LABEL);
            System.out.println(SEPARATOR_LABEL);
        }
        endMessage();
    }

    private static void endMessage() {
        System.out.println("\n\tSBP ended - Bye Bye :)\n");
    }


    private static void parseArgs(String[] args) {
        if (args.length != 2) {
            System.out.println("Server address and port number required at command line.");
            System.out.println("Usage: java Client {SERVER-ADDRESS} {SERVER-PORT-NUMBER}");
            System.exit(1);
        }
        try {
            serverIP = InetAddress.getByName(args[0]);
        } catch (UnknownHostException ex) {
            System.out.println("Invalid SERVER-ADDRESS.");
            System.exit(1);
        }
        //Create socket
        try {
            serverPort = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid SERVER-PORT.");
            System.exit(1);
        }
    }

    public static void setUsername(String username) {
        SBPClientApp.username = username;
    }
} // CLASS