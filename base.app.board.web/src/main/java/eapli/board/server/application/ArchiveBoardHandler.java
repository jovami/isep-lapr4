package eapli.board.server.application;


import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.board.SBProtocol;
import eapli.board.server.SBPServerApp;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.List;

public class ArchiveBoardHandler implements Runnable {

    private DataInputStream inS;
    private DataOutputStream outS;
    private Socket sock;
    private BoardRepository boardRepository = PersistenceContext.repositories().boards();
    private ShareBoardService srv;

    public ArchiveBoardHandler(Socket socket, SBProtocol boardsRequest) {
        Preconditions.areEqual(boardsRequest.getCode(), SBProtocol.GET_BOARDS_OWNED_NOT_ARCHIVED);
        this.sock = socket;
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

            SystemUser owner = SBPServerApp.activeAuths.get(sock.getInetAddress()).getUserLoggedIn();

            StringBuilder builder = new StringBuilder();
            var boards = srv.listBoardsUserOwnsNotArchived(owner);

            //SendBoardOwned
            SBProtocol sendBoards = sendBoardOwned(builder, boards);
            if (sendBoards == null) return;

            //receive board that the user wants to archive
            SBProtocol receiveBoard = new SBProtocol(inS);
            String boardName = receiveBoard.getContentAsString();
            Board board = SBPServerApp.boards.get(BoardTitle.valueOf(boardName));

            //if the board does not exist send ERR
            if (board==null) {
                sendBoards.setCode(SBProtocol.ERR);
                sendBoards.setContentFromString("Board not found");
                sendBoards.send(outS);
                return;
            }
            board.archiveBoard();
            boardRepository.save(board);

            StringBuilder builderArchive = new StringBuilder();
            var boardsArchived = srv.listBoardsUserOwnsArchived(owner);

            //SendBoardOwned
            SBProtocol sendBoardsArchive = sendBoardOwned(builderArchive, boardsArchived);
            if (sendBoardsArchive == null) return;
            sendBoardsArchive.setCode(SBProtocol.GET_BOARDS_OWNED_ARCHIVED);
            sendBoardsArchive.send(outS);


        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }
    }

    private SBProtocol sendBoardOwned(StringBuilder builder, Collection<Board> boards) throws IOException {
        //send boards that the user owns
        SBProtocol sendBoards = new SBProtocol();
        if (boards.isEmpty()) {
            sendBoards.setCode(SBProtocol.ERR);
            sendBoards.setContentFromString("User does not own boards that can possible be archived");
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
