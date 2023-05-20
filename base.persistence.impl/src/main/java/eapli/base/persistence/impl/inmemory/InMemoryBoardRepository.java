package eapli.base.persistence.impl.inmemory;

import java.util.Optional;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryBoardRepository
        extends InMemoryDomainRepository<Board, BoardTitle>
        implements BoardRepository {

    static {
        InMemoryInitializer.init();
    }

    public Optional<Board> findByTitle(final BoardTitle boardTitle) {
        return Optional.of(data().get(boardTitle));
    }

    // TODO i dont know what im i doing
    @Override
    public boolean hasCellPostIt(int cellId) {
        return false;
    }

    @Override
    public boolean isBoardTitleUnique(String boardTitle) {
        return false;
    }
}
