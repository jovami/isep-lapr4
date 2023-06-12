package eapli.board.server.application;

import com.ibm.icu.impl.Pair;
import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipantPermissions;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.board.SBProtocol;
import eapli.board.server.MenuRequest;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.validations.Preconditions;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ShareBoardHandler implements Runnable {
    private DataInputStream inS;
    private DataOutputStream outS;
    private Socket sock;
    private SBProtocol boardsRequest;
    private BoardRepository boardRepository = PersistenceContext.repositories().boards();

    private UserRepository userRepo = PersistenceContext.repositories().users();
    private ShareBoardService srv;

    public ShareBoardHandler(Socket socket, SBProtocol boardsRequest) {
        Preconditions.areEqual(boardsRequest.getCode(), SBProtocol.GET_BOARDS_OWNED);
        this.sock = socket;
        this.boardsRequest = boardsRequest;
        srv = new ShareBoardService();
    }


    public void run() {
        try {
            inS = new DataInputStream(sock.getInputStream());
            outS = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {

            SystemUser owner = MenuRequest.clientBySock(sock.getInetAddress()).getUserLoggedIn();

            StringBuilder builder = new StringBuilder();
            List<Board> boards = srv.listBoardsUserOwns(owner);

            //SendBoardOwned
            SBProtocol sendBoards = sendBoardOwned(builder, boards);
            if (sendBoards == null) return;

            //receive board that the user wants to share
            SBProtocol receiveBoard = new SBProtocol(inS);
            String boardName = receiveBoard.getContentAsString();
            Optional<Board> optBoard = boardRepository.ofIdentity(BoardTitle.valueOf(boardName));

            //if the board does not exist send ERR
            if (optBoard.isEmpty()) {
                sendBoards.setCode(SBProtocol.ERR);
                sendBoards.setContentFromString("Board not found");
                sendBoards.send(outS);
                return;
            }

            //Send users that are not yet invited
            if (!sendUsersNotInvited( optBoard)) return;

            //receive user to invite
            SBProtocol receiveInvited = new SBProtocol(inS);
            SBProtocol sendResponse = new SBProtocol();

            List<Pair<SystemUser, BoardParticipantPermissions>> usersToInvite = new ArrayList<>();
            Optional<SystemUser> optUser;
            String[] str;
            String username, permStr;
            BoardParticipantPermissions perm=null;

            for (String user : List.of(receiveInvited.getContentAsString().split("\0"))) {
                str = user.split(" ");
                username = str[0];
                permStr = str[1];

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

            srv.shareBoard(optBoard.get(), usersToInvite);
            sendResponse.setCode(SBProtocol.ACK);
            sendResponse.send(outS);

        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }
    }

    private boolean sendUsersNotInvited(Optional<Board> optBoard) throws IOException {
        StringBuilder builder;
        SBProtocol sendUsers = new SBProtocol();
        List<SystemUser> users = srv.usersNotInvited(optBoard.get());
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
        //sendBoards.setCode();
        sendUsers.setContentFromString(builder.toString());
        sendUsers.send(outS);
        return true;
    }

    private SBProtocol sendBoardOwned(StringBuilder builder, List<Board> boards) throws IOException {
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
            //sendBoards.setCode(SBPMessage.SEND_BOARDS);
            String send = builder.toString();
            //sendBoards.setCode(255);
            sendBoards.setContentFromString(builder.toString());
        }
        sendBoards.send(outS);
        return sendBoards;
    }
}
