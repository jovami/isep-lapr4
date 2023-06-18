package eapli.base.persistence.impl.inmemory;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;
import eapli.base.board.domain.BoardParticipantPermissions;
import eapli.base.board.repositories.BoardParticipantRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.stream.Collectors;

public class InMemoryBoardParticipantRepository extends InMemoryDomainRepository<BoardParticipant, Integer>
        implements BoardParticipantRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Iterable<BoardParticipant> listBoardParticipants(Board board) {

        return valuesStream()
                .filter(boardParticipant -> board.sameAs(board))
                .collect(Collectors.toList());

    }

   /* @Override
    public Iterable<BoardParticipant> listBoardUserLoggedParticipates(SystemUser systemUser) {

        return valuesStream()
                .filter(boardParticipant -> boardParticipant.sameAs(systemUser))
                .collect(Collectors.toList());
    }*/


    @Override
    public Iterable<Board> listBoardsByParticipant(SystemUser user) {
        return valuesStream()
                .filter(boardParticipant -> boardParticipant.participant().sameAs(user))
                .map(BoardParticipant::board).collect(Collectors.toList());
    }

    @Override
    public Iterable<Board> withPermission(SystemUser user, BoardParticipantPermissions perm) {
        return valuesStream()
                .filter(boardParticipant -> boardParticipant.participant().sameAs(user)
                        &&boardParticipant.permission().equals(perm) )
                .map(BoardParticipant::board).collect(Collectors.toList());
    }

    @Override
    public Iterable<BoardParticipant> byUser(SystemUser user) {
        return valuesStream()
                .filter(boardParticipant -> boardParticipant.sameAs(user))
                .collect(Collectors.toList());
    }
    @Override
    public Iterable<Board> listBoardsUserParticipatesNotArchived(SystemUser user) {
        return valuesStream()
                .filter(boardParticipant -> {
                    if (!boardParticipant.participant().sameAs(user)
                            || !boardParticipant.permission().equals(BoardParticipantPermissions.READ)) return false;
                    boardParticipant.board();
                    return true;
                })
                .map(BoardParticipant::board).collect(Collectors.toList());
    }


}
