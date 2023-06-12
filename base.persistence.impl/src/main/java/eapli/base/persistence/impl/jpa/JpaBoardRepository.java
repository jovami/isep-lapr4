package eapli.base.persistence.impl.jpa;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.Optional;

class JpaBoardRepository extends BaseJpaRepositoryBase<Board, Long, BoardTitle> implements BoardRepository {

    JpaBoardRepository(String persistenceUnitName) {
        super(persistenceUnitName, "boardTitle");
    }

    public boolean hasCellPostIt(int cellId) {
        Optional<Board> findIfCellHasPostIt = matchOne(
                "e.cellId = :cellId", cellId);
        return findIfCellHasPostIt.isPresent();

    }

    public boolean isBoardTitleUnique(String otherBoardTitle) {
        Optional<Board> findIfBoardTitleIsUnique = matchOne(
                "e.title = :otherBoardTitle", otherBoardTitle);
        return findIfBoardTitleIsUnique.isEmpty();
    }


    @Override
    public Iterable<Board> listBoardsUserOwns(SystemUser owner)
    {
        final var query = entityManager().createQuery(
                "SELECT e FROM Board e WHERE e.owner = :owner ",
                Board.class);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

    @Override
    public Iterable<Board> listBoardsUserOwnsNotArchived(SystemUser owner)
    {
        final var query = entityManager().createQuery(
                "SELECT e FROM Board e WHERE e.owner = :owner And e.state <> 'ARCHIVED'",
                Board.class);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

    @Override
    public Iterable<Board> listBoardsUserOwnsArchived(SystemUser owner)
    {
        final var query = entityManager().createQuery(
                "SELECT e FROM Board e WHERE e.owner = :owner And e.state = 'ARCHIVED'",
                Board.class);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

}
