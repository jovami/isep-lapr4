package eapli.base.persistence.impl.inmemory;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;
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
                .filter(boardParticipant -> boardParticipant.sameAs(board))
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<BoardParticipant> listBoardUserLoggedParticipates(SystemUser systemUser) {

        return valuesStream()
                .filter(boardParticipant -> boardParticipant.sameAs(systemUser))
                .collect(Collectors.toList());

    }


}
