package eapli.base.board.repositories;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

public interface BoardRepository extends DomainRepository<BoardTitle, Board> {

    boolean hasCellPostIt(int cellId);

    boolean isBoardTitleUnique(String boardTitle);
    Iterable<Board> listBoardsUserOwns(SystemUser owner);
    Iterable<Board> listBoardsUserOwnsNotArchived(SystemUser owner);
    Iterable<Board> listBoardsUserOwnsArchived(SystemUser owner);
}
