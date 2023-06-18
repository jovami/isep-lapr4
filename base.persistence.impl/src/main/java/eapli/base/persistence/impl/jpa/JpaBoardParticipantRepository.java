package eapli.base.persistence.impl.jpa;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;
import eapli.base.board.domain.BoardParticipantPermissions;
import eapli.base.board.repositories.BoardParticipantRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import org.hibernate.sql.Select;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.List;
import java.util.Map;

public class JpaBoardParticipantRepository extends JpaAutoTxRepository<BoardParticipant, Integer, Integer> implements BoardParticipantRepository {

    JpaBoardParticipantRepository(final TransactionalContext autoTx) {
        super(autoTx, "participantId");
    }

    public JpaBoardParticipantRepository(final String puname,
                                         @SuppressWarnings({"rawtypes", "java:S3740"}) final Map properties) {
        super(puname, properties, "participantId");

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
                        " AND bp.permission = :perm",
                Board.class);
        query.setParameter("user", user);
        query.setParameter("perm", perm);
        return query.getResultList();
    }

    @Override
    public Iterable<BoardParticipant> byUser(SystemUser user) {
        return match("e.participant = :user", "user", user);
    }

    @Override
    public Iterable<Board> listBoardsUserParticipatesNotArchived(SystemUser user) {
        final var query = entityManager().createQuery(
                "SELECT e.board FROM BoardParticipant e WHERE ((e.participant = :user And e.board.state <> 'ARCHIVED' " +
                        "And (e.permission = 'READ' OR e.permission = 'WRITE')))",
                Board.class);
        query.setParameter("user", user);
        return query.getResultList();
    }


}

