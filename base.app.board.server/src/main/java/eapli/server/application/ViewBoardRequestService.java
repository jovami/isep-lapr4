package eapli.server.application;

import eapli.base.board.domain.Board;
import eapli.base.board.repositories.BoardParticipantRepository;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.impl.factory.Lists;

import java.util.Collection;

public class ViewBoardRequestService {


    private final TransactionalContext txCtx = PersistenceContext.repositories()
            .newTransactionalContext();
    private final BoardRepository boardRepository;
    private final BoardParticipantRepository boardParticipantRepository;


    public ViewBoardRequestService() {
        boardRepository = PersistenceContext.repositories().boards();
        boardParticipantRepository = PersistenceContext.repositories().boardParticipants(txCtx);
    }


    public Collection<Board> listBoardsUserOwnsNotArchived(SystemUser user) {
        return Lists.mutable.ofAll(boardRepository.listBoardsUserOwnsNotArchived(user));
    }
    public Collection<Board> listBoardsWithPermByParticipant(SystemUser user) {
        return Lists.mutable.ofAll(boardParticipantRepository.listBoardsUserParticipatesNotArchived(user));
    }
    public Collection<Board> listReadableNonArchivedBoardsForUser(SystemUser user) {
        MutableCollection<Board> combinedCollection = Lists.mutable.empty();

        combinedCollection.addAll(listBoardsUserOwnsNotArchived(user));
        combinedCollection.addAll(listBoardsWithPermByParticipant(user));

        return combinedCollection;
    }
}
