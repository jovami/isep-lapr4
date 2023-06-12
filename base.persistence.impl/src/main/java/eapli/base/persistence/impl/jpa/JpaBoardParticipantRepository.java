package eapli.base.persistence.impl.jpa;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;
import eapli.base.board.domain.BoardParticipantPermissions;
import eapli.base.board.repositories.BoardParticipantRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.List;

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



    public List<Board> listBoardsByParticipant(SystemUser user) {

        final var query = entityManager().createQuery(
                "SELECT bp.board FROM BoardParticipant bp WHERE bp.participant = :user",
                Board.class);
        query.setParameter("user", user);
        return query.getResultList();

    }

    @Override
    public Iterable<Board> withPermission(SystemUser user, BoardParticipantPermissions perm) {
        final var query = entityManager().createQuery(
                "SELECT bp.board FROM BoardParticipant bp " +
                        "WHERE bp.participant = :user" +
                        "AND bp.permission = :perm",
                Board.class);
        query.setParameter("user", user);
        query.setParameter("perm", perm);
        return query.getResultList();
    }
}

