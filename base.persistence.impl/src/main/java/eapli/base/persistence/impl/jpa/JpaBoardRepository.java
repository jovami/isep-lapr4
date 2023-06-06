package eapli.base.persistence.impl.jpa;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;

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
}
