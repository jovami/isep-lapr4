package eapli.board.server;

import eapli.board.SBPMessage;
import eapli.board.server.application.AuthRequestHandler;
import eapli.board.server.application.DisconnRequestHandler;
import eapli.board.server.domain.Client;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class MenuRequest extends Thread {
    // private String baseFolder;
    private final Socket sock;
    private static final HashMap<InetAddress, Client> activeAuths = new HashMap<>();

    public MenuRequest(Socket s) {
        sock = s;
    }

    public void run() {
        try {
            DataInputStream inS = new DataInputStream(sock.getInputStream());

            SBPMessage request = new SBPMessage(inS);

            // If this IP is not registered yet in the system , only allow codes lower than 4
            if (activeAuths.get(sock.getInetAddress()) == null && request.getCode() > 4) {
                System.out.printf("[WARNING] %s tried to access code %d without authentication\n", sock.getInetAddress().toString(), request.getCode());
                //Should close socket??
                return;
            }

            //depending on the SBPMessageCode

            switch (request.getCode()) {
                case SBPMessage.AUTH:
                    AuthRequestHandler authRequest = new AuthRequestHandler(sock, request);
                    authRequest.run();
                    break;
                case SBPMessage.DISCONN:
                    DisconnRequestHandler disconn = new DisconnRequestHandler(sock, request);
                    disconn.run();
                    break;
                /*case SBPMessage.GET_BOARDS_OWNED:
                    ShareBoardHandler getBoardsOwner = new ShareBoardHandler(sock, request);
                    getBoardsOwner.run();
                    break;
                case SBPMessage.VIEW_ALL_BOARDS:
                    ViewBoardRequestHandler view_boards = new ViewBoardRequestHandler(sock, request);
                    view_boards.run();
                    break;*/
            }

        } catch (IOException ex) {
            System.out.printf("[WARNING] Thread error when reading request for %s",sock.getInetAddress());
        } catch (ReceivedERRCode er) {
            System.out.println("[ERR RECEIVED] " + er.getMessage());
        }

    }

    public synchronized static boolean addInetAddress(SystemUser user, InetAddress inetAddress) {
        return activeAuths.putIfAbsent(inetAddress, new Client(inetAddress, user)) == null;
    }

    public synchronized static Client remove(InetAddress inetAddress) {
        return activeAuths.remove(inetAddress);
    }


}