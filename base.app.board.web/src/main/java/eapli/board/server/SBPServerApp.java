package eapli.board.server;

import eapli.base.app.common.console.BaseApplication;
import eapli.base.board.domain.Board;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.clientusermanagement.domain.events.NewUserRegisteredFromSignupEvent;
import eapli.base.clientusermanagement.usermanagement.domain.BasePasswordPolicy;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.board.server.application.newChangeEvent.NewChangeEvent;
import eapli.board.server.application.newChangeEvent.NewChangeWatchDog;
import eapli.board.server.domain.BoardHistory;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.pubsub.EventDispatcher;
import lombok.Getter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class SBPServerApp extends BaseApplication {

    static private ServerSocket sock;

    public static HashMap<Board,BoardHistory> boardHistory = new HashMap<>();
    private static final BoardRepository boardRepository = PersistenceContext.repositories().boards();
    private static final String SEPARATOR_LABEL = "----------------------------------";

    /**
     * avoid instantiation of this class.
     */
    private SBPServerApp() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {

        AuthzRegistry.configure(PersistenceContext.repositories().users(), new BasePasswordPolicy(),
                new PlainTextEncoder());

        boardRepository.findAll().forEach(board -> boardHistory.put(board, new BoardHistory()));
        new SBPServerApp().run(args);
    }

    @Override
    protected void doMain(final String[] args) {
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
        try {
            while (true) {
                cliSock = sock.accept();
                MenuRequest req = new MenuRequest(cliSock);
                req.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String appTitle() {
        return "SBServer";
    }

    @Override
    protected String appGoodbye() {
        return "SBServer";
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doSetupEventHandlers(final EventDispatcher dispatcher) {
        dispatcher.subscribe(new NewChangeWatchDog(), NewChangeEvent.class);
    }
}