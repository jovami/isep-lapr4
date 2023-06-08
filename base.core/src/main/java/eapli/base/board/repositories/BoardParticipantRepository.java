package eapli.base.board.repositories;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;
import eapli.base.board.domain.BoardTitle;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

public interface BoardParticipantRepository extends DomainRepository<Integer,BoardParticipant> {

    Iterable<BoardParticipant> listBoardParticipants(Board board);
}
