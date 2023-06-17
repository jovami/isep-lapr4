package eapli.board.server;

import eapli.base.app.common.console.BaseApplication;
import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BasePasswordPolicy;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.board.server.application.newChangeEvent.NewChangeEvent;
import eapli.board.server.application.newChangeEvent.NewChangeWatchDog;
import eapli.board.server.domain.Client;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.pubsub.EventDispatcher;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SBServerApp extends BaseApplication {

    // TODO: CHANGE TO TOKEN
    public static final Map<InetAddress, Client> activeAuths;
    public static final Map<BoardTitle, Board> boards;

    static {
        boards = Collections.synchronizedMap(new HashMap<>());
        activeAuths = Collections.synchronizedMap(new HashMap<>());
    }

    private static ServerSocket sock;
    public static final String SEPARATOR_LABEL = "----------------------------------";

    private final BoardRepository boardRepository = PersistenceContext.repositories().boards();

    /**
     * avoid instantiation of this class.
     */
    private SBServerApp() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {

        AuthzRegistry.configure(PersistenceContext.repositories().users(), new BasePasswordPolicy(),
                new PlainTextEncoder());

        new SBServerApp().run(args);
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

        // setup in memory boards
        for (Board board : boardRepository.findAll()) {
            boards.putIfAbsent(board.getBoardTitle(), board);
        }

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
