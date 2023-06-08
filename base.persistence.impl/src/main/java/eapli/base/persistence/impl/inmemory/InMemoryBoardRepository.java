package eapli.base.persistence.impl.inmemory;

import java.util.Optional;
import java.util.stream.Collectors;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.course.domain.CourseState;
import eapli.base.enrollment.domain.Enrollment;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
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

    public Iterable<Board> listBoardsUserOwns(SystemUser owner)
    {
        return valuesStream()
                .filter(board -> board.boardOwner().sameAs(owner))
                .collect(Collectors.toList());
    }

}
