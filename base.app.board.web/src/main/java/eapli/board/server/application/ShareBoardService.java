package eapli.board.server.application;

import com.ibm.icu.impl.Pair;
import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;
import eapli.base.board.domain.BoardParticipantPermissions;
import eapli.base.board.repositories.BoardParticipantRepository;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShareBoardService {
    private final TransactionalContext txCtx = PersistenceContext.repositories()
            .newTransactionalContext();
    private ArrayList<SystemUser> users;
    private final BoardRepository boardRepository;
    private final BoardParticipantRepository boardParticipantRepository;
    private final UserRepository userRepository;


    public ShareBoardService() {
        boardRepository = PersistenceContext.repositories().boards();
        boardParticipantRepository = PersistenceContext.repositories().boardParticipants();
        userRepository = PersistenceContext.repositories().users();
    }

    public List<Board> listBoardsUserOwns(SystemUser user) {
        return (ArrayList<Board>) boardRepository.listBoardsUserOwns(user);
    }

    public List<Board> listBoardsUserOwnsNotArchived(SystemUser user) {
        return (ArrayList<Board>) boardRepository.listBoardsUserOwnsNotArchived(user);
    }

    public List<Board> listBoardsUserOwnsArchived(SystemUser user) {
        return (ArrayList<Board>) boardRepository.listBoardsUserOwnsArchived(user);
    }

    public boolean shareBoard(Board board, List<Pair<SystemUser,BoardParticipantPermissions>> users) {
        //txCtx.beginTransaction();

        try {

            for (Pair<SystemUser,BoardParticipantPermissions> pair : users) {
                BoardParticipant boardParticipant = new BoardParticipant(board, pair.first, pair.second);
                boardParticipantRepository.save(boardParticipant);
            }
        }catch (ConcurrencyException e){
            //txCtx.rollback
            return false;
        }

        //txCtx.commit();

        return true;
    }

    public List<Board> getBoardsByParticipant(SystemUser user) {
        return (List<Board>) boardParticipantRepository.listBoardsByParticipant(user);
    }

    public List<SystemUser> usersNotInvited(Board board) {
        users = (ArrayList<SystemUser>) userRepository.findAll();
        for (BoardParticipant participant : boardParticipantRepository.listBoardParticipants(board)) {
            users.remove(participant.participant());
        }

        // TODO: isto nao funfa pq ta do lado do server
        // usar token/username/whateverelse
        MyUserService s = new MyUserService();
        users.remove(s.currentUser());
        return users;
    }

    public List<Board> listBoardsUserParticipatesAndHasWritePermissionsPlusBoardOwnsNotArchived(SystemUser user) {

        Iterable<Board> boardParticipant = boardParticipantRepository.withPermission(user,BoardParticipantPermissions.WRITE);
        List<Board> listBoardsUserOwnsNotArchived = listBoardsUserOwnsNotArchived(user);
        listBoardsUserOwnsNotArchived.addAll((Collection<? extends Board>) boardParticipant);

        /*for(Board board :listBoardsUserOwnsNotArchived)
        {
            System.out.println("Boardowns= "+board.getBoardTitle().title() +" BoardColumns= " +board.getBoardColumnList().size() +
                                " Boardrowns= " +board.getBoardRowList().size());
        }*/

        return listBoardsUserOwnsNotArchived;
    }

}
