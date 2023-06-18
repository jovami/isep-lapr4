package eapli.server.application;

import com.ibm.icu.impl.Pair;
import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipantPermissions;
import eapli.base.board.domain.BoardTitle;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.board.SBProtocol;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.server.SBServerApp;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ShareBoardHandler extends AbstractSBServerHandler {
    private final UserRepository userRepo = PersistenceContext.repositories().users();
    private final ShareBoardService srv = new ShareBoardService();;

    public ShareBoardHandler(Socket socket, SBProtocol boardsRequest) {
        super(socket,boardsRequest);
    }

    public void run() {
        try {


            SystemUser owner = SBServerApp.activeAuths.get(authToken).getUserLoggedIn();

            StringBuilder builder = new StringBuilder();

            var boards = srv.listBoardsUserOwnsNotArchived(owner);

            //SendBoardOwned
            SBProtocol sendBoards = sendBoardsOwned(builder, boards);
            if (sendBoards == null) return;

            //receive board that the user wants to share
            SBProtocol receiveBoard = new SBProtocol(inS);
            String boardName = receiveBoard.getContentAsString();
            Board optBoard =  SBServerApp.boards.get(BoardTitle.valueOf(boardName));

            //if the board does not exist send ERR
            if (optBoard==null) {
                sendBoards.setCode(SBProtocol.ERR);
                sendBoards.setContentFromString("Board not found");
                sendBoards.send(outS);
                return;
            }

            //Send users that are not yet invited
            if (!sendUsersNotInvited(optBoard)) return;

            //receive user to invite
            SBProtocol receiveInvited = new SBProtocol(inS);

            List<Pair<SystemUser, BoardParticipantPermissions>> usersToInvite = inviteUsers(receiveInvited.getContentAsString().split("\0"));

            srv.shareBoard(optBoard, usersToInvite);

            SBProtocol ack = new SBProtocol();
            ack.setCode(SBProtocol.ACK);
            ack.send(outS);

            System.out.printf("[INFO] %s participants updated\n",boardName);

        } catch (IOException | ReceivedERRCode ignored) {
        }
    }

    private List<Pair<SystemUser, BoardParticipantPermissions>> inviteUsers(String[] invites) throws IOException {
        List<Pair<SystemUser, BoardParticipantPermissions>> usersToInvite = new ArrayList<>();

        Optional<SystemUser> optUser;
        BoardParticipantPermissions perm;
        String username;
        String permStr;
        SBProtocol sendResponse = new SBProtocol();
        for (String user : invites) {
            username = user.split("#&&#")[0];
            permStr = user.split("#&&#")[1];

            if (permStr.equals(BoardParticipantPermissions.WRITE.toString())) {
                perm = BoardParticipantPermissions.WRITE;
            }else{
                perm = BoardParticipantPermissions.READ;
            }

            optUser = userRepo.ofIdentity(Username.valueOf(username));
            if (optUser.isEmpty()) {
                sendResponse.setCode(SBProtocol.ERR);
                sendResponse.setContentFromString("User not recognized");
                sendResponse.send(outS);
                continue;
            }
            usersToInvite.add(Pair.of(optUser.get(),perm));
        }
        return usersToInvite;
    }

    private boolean sendUsersNotInvited(Board optBoard) throws IOException {
        StringBuilder builder;
        SBProtocol sendUsers = new SBProtocol();
        var users = srv.usersNotInvited(optBoard);
        if (users.isEmpty()) {
            sendUsers.setCode(SBProtocol.ERR);
            sendUsers.setContentFromString("No users to invite");
            sendUsers.send(outS);
            return false;
        }

        builder = new StringBuilder();
        for (SystemUser user : users) {
            builder.append(user.username().toString()).append('\0');
        }
        sendUsers.setContentFromString(builder.toString());
        sendUsers.send(outS);
        return true;
    }

    private SBProtocol sendBoardsOwned(StringBuilder builder, Collection<Board> boards) throws IOException {
        //send boards that the user owns
        SBProtocol sendBoards = new SBProtocol();
        if (boards.isEmpty()) {
            sendBoards.setCode(SBProtocol.ERR);
            sendBoards.setContentFromString("User does not own boards");
            sendBoards.send(outS);
            return null;
        } else {
            for (Board b : boards) {
                builder.append(b.getBoardTitle().title()).append('\0');
            }
            sendBoards.setContentFromString(builder.toString());
        }
        sendBoards.send(outS);
        return sendBoards;
    }
}
