package eapli.base.persistence.impl.jpa;

import java.util.Optional;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;

class JpaBoardRepository extends BaseJpaRepositoryBase<Board, Long, Integer> implements BoardRepository {

    JpaBoardRepository(String persistenceUnitName) {
        super(persistenceUnitName, "BoardTitle");
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
    public Optional<Board> ofIdentity(BoardTitle id) {
        return Optional.empty();
    }

    @Override
    public void deleteOfIdentity(BoardTitle entityId) {

    }
}
