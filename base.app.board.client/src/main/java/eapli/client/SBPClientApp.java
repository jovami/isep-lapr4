package eapli.client;

import eapli.base.app.common.console.BaseApplication;
import eapli.base.clientusermanagement.usermanagement.domain.BasePasswordPolicy;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.client.application.AJAX.ClientServerAjax;
import eapli.client.application.CommTestController;
import eapli.client.application.DisconnRequestController;
import eapli.client.presentation.AuthRequestUI;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.pubsub.EventDispatcher;
import jovami.util.exceptions.ReceivedERRCode;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SBPClientApp extends BaseApplication {
    static private InetAddress serverIP;
    static private int serverPort;
    private static final String SEPARATOR_LABEL = "------------------------------";
    private static String authToken;

    private SBPClientApp() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {

        AuthzRegistry.configure(PersistenceContext.repositories().users(), new BasePasswordPolicy(),
                new PlainTextEncoder());

        new SBPClientApp().run(args);
    }

    public synchronized static void setToken(String content) {
        authToken = content;
    }
    public synchronized static String authToken() {
        return authToken;
    }

    @Override
    protected void doMain(final String[] args) {
        parseArgs(args);

        testConnection();

        //Before any action, the user is forced to login
        AuthRequestUI auth = new AuthRequestUI(serverIP, serverPort);
        if (!auth.show()) {
            //if login failed terminate app
            endMessage();
            return;
        }

        //DO WORK
        try {
            ClientServerAjax cliServAjax = new ClientServerAjax();
            cliServAjax.start();
            MainMenu menu = new MainMenu(serverIP, serverPort);
            menu.mainLoop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    private void testConnection() {
        CommTestController ctrl = new CommTestController(serverIP, serverPort);

        try {
            if (ctrl.commTest()) {
                System.out.println(SEPARATOR_LABEL);
                System.out.println(SEPARATOR_LABEL + "\nConnected to SBServer\n");
                System.out.println(SEPARATOR_LABEL);
            } else {
                System.out.println(SEPARATOR_LABEL);
                System.out.println(SEPARATOR_LABEL + "\nUnsuccessfully Disconnected from SBP server\n");
                System.out.println(SEPARATOR_LABEL);
            }
        } catch (ReceivedERRCode | RuntimeException e) {
            System.out.println(SEPARATOR_LABEL);
            System.out.println(SEPARATOR_LABEL + "\n"+e.getMessage()+"\n");
            System.out.println(SEPARATOR_LABEL);
            System.exit(1);
        }
    }

    private static void disconnect() {
        DisconnRequestController ctrl = new DisconnRequestController(serverIP, serverPort);

        if (ctrl.disconn()) {
            System.out.println(SEPARATOR_LABEL);
            System.out.println(SEPARATOR_LABEL + "\nDisconnected Successfully from SBP server\n");
            System.out.println(SEPARATOR_LABEL);
        } else {
            System.out.println(SEPARATOR_LABEL);
            System.out.println(SEPARATOR_LABEL + "\nUnsuccessfully Disconnected from SBP server\n");
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

    @Override
    protected String appTitle() {
        return "SBApp";
    }

    @Override
    protected String appGoodbye() {
        return "SBApp";
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doSetupEventHandlers(final EventDispatcher dispatcher) {
        return;
    }
} // CLASS
