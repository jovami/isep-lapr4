package eapli.server;

import eapli.board.SBProtocol;
import eapli.server.application.*;
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
            if (request.getCode() > 4 || request.getCode()==SBProtocol.DISCONN) {
                if (request.token()==null) {
                    System.out.println("[WARNING] %s tried to access features without authentication tokens");
                    return;
                }
                if (SBServerApp.activeAuths.get(request.token()) == null) {
                    System.out.println("[WARNING] %s tried to access features without being logged in first");
                    return;
                }
            }


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
                case SBProtocol.SHARE_BOARD:
                    ShareBoardHandler getBoardsOwner = new ShareBoardHandler(sock, request);
                    getBoardsOwner.run();
                    break;
                case SBProtocol.VIEW_ALL_BOARDS:
                    ViewBoardRequestHandler view_boards = new ViewBoardRequestHandler(sock, request);
                    view_boards.run();
                    break;
                case SBProtocol.GET_BOARDS_OWNED_NOT_ARCHIVED:
                    ArchiveBoardHandler getBoardsOwnerNotArchived = new ArchiveBoardHandler(sock, request);
                    getBoardsOwnerNotArchived.run();
                    break;
                case SBProtocol.CREATE_POST_IT:
                    CreatePostItHandler createPostIt = new CreatePostItHandler(sock, request);
                    createPostIt.run();
                    break;
                case SBProtocol.UPDATE_POST_IT:
                    UpdatePostItHandler updatePostIt = new UpdatePostItHandler(sock, request);
                    updatePostIt.run();
                    break;
                case SBProtocol.MOVE_POST_IT:
                    new MovePostItHandler(sock, request).run();
                    break;
                case SBProtocol.LIST_HISTORY:
                    ViewBoardHistoryHandler viewBoardHistoryHandler = new ViewBoardHistoryHandler(sock, request);
                    viewBoardHistoryHandler.run();
                    break;
                case SBProtocol.UNDO_LAST_POST_IT_CHANGE:
                    new UndoPostItLastChangeHandler(sock, request).run();
                    break;
                case SBProtocol.VIEW_NOTFS:
                    NotificationsRequestHandler notfHandler = new NotificationsRequestHandler(sock, request);
                    notfHandler.run();
                    break;
            }

        } catch (IOException ignored) {
        } catch (ReceivedERRCode er) {
            System.out.println("[ERR RECEIVED] " + er.getMessage());
        }

    }


}
