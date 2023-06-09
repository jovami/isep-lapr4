package eapli.base.persistence.impl.jpa;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;
import eapli.base.board.repositories.BoardParticipantRepository;
import eapli.base.event.lecture.domain.Lecture;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

public class JpaBoardParticipantRepository extends BaseJpaRepositoryBase<BoardParticipant, Long, Integer> implements BoardParticipantRepository {

    JpaBoardParticipantRepository(String persistenceUnitName) {
        super(persistenceUnitName, "BoardParticipant");
    }

    @Override
    public Iterable<BoardParticipant> listBoardParticipants(Board board) {

        final var query = entityManager().createQuery(
                "SELECT sm FROM BoardParticipant sm WHERE sm.board = :board",
                BoardParticipant.class);
        query.setParameter("board", board);
        return query.getResultList();

    }
/*
    public Iterable<SystemUser> listBoardUsers(Board board) {

        final var query = entityManager().createQuery(
                "SELECT sm.participant FROM BoardParticipant sm WHERE sm.board = :board",
                BoardParticipant.class);
        query.setParameter("board", board);
        return query.getResultList();

    }*/
}
