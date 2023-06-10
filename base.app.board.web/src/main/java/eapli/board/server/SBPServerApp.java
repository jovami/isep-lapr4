package eapli.board.server;

import eapli.base.clientusermanagement.usermanagement.domain.BasePasswordPolicy;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SBPServerApp {
    static private ServerSocket sock;
    private static final String SEPARATOR_LABEL = "----------------------------------";

    public static void main(String args[]) throws Exception {

        AuthzRegistry.configure(PersistenceContext.repositories().users(), new BasePasswordPolicy(),
                new PlainTextEncoder());

        System.out.println("\n\n" + SEPARATOR_LABEL);
        System.out.println("Shared Board Server - (SBP server)");
        System.out.println(SEPARATOR_LABEL + "\n");

        if (args.length != 1) {
            System.out.println("Local port number required at the command line.");
            System.exit(1);
        }

        Socket cliSock;
        try {
            sock = new ServerSocket(Integer.parseInt(args[0]));
        } catch (IOException ex) {
            System.out.println("Server failed to open local port " + args[0]);
            System.exit(1);
        }

        //KEEPS LISTENING to new sockets on port args[0]
        //TODO: check if this can be a new thread??
        while (true) {
            cliSock = sock.accept();
            MenuRequest req = new MenuRequest(cliSock);
            req.start();
        }
    }
}