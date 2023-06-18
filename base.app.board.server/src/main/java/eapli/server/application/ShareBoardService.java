package eapli.server.application;

import com.ibm.icu.impl.Pair;
import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;
import eapli.base.board.domain.BoardParticipantPermissions;
import eapli.base.board.repositories.BoardParticipantRepository;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;

import java.util.Collection;
import java.util.List;

public class ShareBoardService {
    private final TransactionalContext txCtx = PersistenceContext.repositories()
            .newTransactionalContext();
    private final BoardRepository boardRepository;
    private final BoardParticipantRepository boardParticipantRepository;
    private final UserRepository userRepository;


    public ShareBoardService() {
        boardRepository = PersistenceContext.repositories().boards();
        boardParticipantRepository = PersistenceContext.repositories().boardParticipants(txCtx);
        userRepository = PersistenceContext.repositories().users();
    }

    public Collection<Board> listBoardsUserOwns(SystemUser user) {
        return Lists.mutable.ofAll(boardRepository.listBoardsUserOwns(user));
    }

    public Collection<Board> listBoardsUserOwnsNotArchived(SystemUser user) {
        return Lists.mutable.ofAll(boardRepository.listBoardsUserOwnsNotArchived(user));
    }

    public Collection<Board> listBoardsUserOwnsArchived(SystemUser user) {
        return Lists.mutable.ofAll(boardRepository.listBoardsUserOwnsArchived(user));
    }

    public Collection<Board> getBoardsByParticipant(SystemUser user) {
        return Lists.mutable.ofAll(boardParticipantRepository.listBoardsByParticipant(user));
    }

    public boolean shareBoard(Board board, List<Pair<SystemUser, BoardParticipantPermissions>> users) {
        txCtx.beginTransaction();
        try {
            for (Pair<SystemUser, BoardParticipantPermissions> pair : users) {
                BoardParticipant boardParticipant = new BoardParticipant(board, pair.first, pair.second);
                boardParticipantRepository.save(boardParticipant);
            }
        } catch (ConcurrencyException e) {
            return false;
        }
        txCtx.commit();
        return true;
    }

    public Collection<SystemUser> usersNotInvited(Board board) {
        var users = Sets.mutable.ofAll(userRepository.findAll());

        for (var participant : boardParticipantRepository.listBoardParticipants(board)) {
            users.remove(participant.participant());
        }

        users.remove(board.boardOwner());
        return users;
    }

    public Collection<Board> boardsUserCanWrite(SystemUser user) {
        var boardParticipant = boardParticipantRepository.withPermission(user, BoardParticipantPermissions.WRITE);
        var listBoardsUserOwnsNotArchived = listBoardsUserOwnsNotArchived(user);

        boardParticipant.forEach(listBoardsUserOwnsNotArchived::add);

        return listBoardsUserOwnsNotArchived;
    }


}
