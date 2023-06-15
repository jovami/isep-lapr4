package eapli.board.server;

import eapli.board.SBProtocol;
import eapli.board.server.application.*;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class MenuRequest extends Thread {
    // private String baseFolder;
    private final Socket sock;

    public MenuRequest(Socket s) {
        sock = s;
    }

    public void run() {
        try {
            DataInputStream inS = new DataInputStream(sock.getInputStream());

            SBProtocol request = new SBProtocol(inS);

            // If this IP is not registered yet in the system , only allow codes lower than 4
            if (SBPServerApp.activeAuths.get(sock.getInetAddress()) == null && request.getCode() > 4) {
                System.out.printf("[WARNING] %s tried to access code %d without authentication\n", sock.getInetAddress().toString(), request.getCode());
                //Should close socket??
                return;
            }

            //depending on the SBPMessageCode

            switch (request.getCode()) {
                case SBProtocol.COMMTEST:
                    CommTestRequestHandler commTest = new CommTestRequestHandler(sock, request);
                    commTest.run();
                    break;
                case SBProtocol.AUTH:
                    AuthRequestHandler authRequest = new AuthRequestHandler(sock, request);
                    authRequest.run();
                    break;
                case SBProtocol.DISCONN:
                    DisconnRequestHandler disconn = new DisconnRequestHandler(sock, request);
                    disconn.run();
                    break;
                case SBProtocol.GET_BOARDS_OWNED:
                    ShareBoardHandler getBoardsOwner = new ShareBoardHandler(sock, request);
                    getBoardsOwner.run();
                    break;
                case SBProtocol.VIEW_ALL_BOARDS:
                    ViewBoardRequestHandler view_boards = new ViewBoardRequestHandler(sock, request);
                    view_boards.run();
                    break;
                case SBProtocol.GET_BOARDS_OWNED_NOT_ARCHIVED:
                    ArchiveBoardHandler getBoardsOwnerNotArchived = new ArchiveBoardHandler(sock,request);
                    getBoardsOwnerNotArchived.run();
                    break;
                case SBProtocol.CREATE_POST_IT:
                    CreatePostItHandler createPostIt = new CreatePostItHandler(sock, request);
                    createPostIt.run();
                    break;
                case SBProtocol.LIST_HISTORY:
                    ViewBoardHistoryHandler viewBoardHistoryHandler = new ViewBoardHistoryHandler(sock, request);
                    viewBoardHistoryHandler.run();
                    break;
            }

        } catch (IOException ex) {
            System.out.printf("[WARNING] Thread error when reading request for %s",sock.getInetAddress());
        } catch (ReceivedERRCode er) {
            System.out.println("[ERR RECEIVED] " + er.getMessage());
        }

    }


}