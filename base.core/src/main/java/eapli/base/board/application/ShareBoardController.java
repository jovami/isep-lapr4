package eapli.base.board.application;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;
import eapli.base.clientusermanagement.dto.SystemUserNameEmailDTO;
import eapli.framework.application.UseCaseController;
import java.util.List;

@UseCaseController
public class ShareBoardController {

    private final ShareBoardService svc;

    public ShareBoardController() {

        this.svc = new ShareBoardService();
    }

    public Iterable<Board> listBoardsUserOwns() {
        return this.svc.listBoardsUserOwns();
    }

    public boolean shareBoard(Board board,List<SystemUserNameEmailDTO> users) {
        return this.svc.shareBoard(board,users);
    }

    public Iterable<BoardParticipant> boardParticipants(Board board) {
         return this.svc.boardParticipants(board);
    }

    public List<SystemUserNameEmailDTO> Users() {
        return this.svc.Users();
    }

}
