package eapli.base.board.repositories;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.framework.domain.repositories.DomainRepository;


public interface BoardRepository extends DomainRepository<BoardTitle, Board> {

    boolean hasCellPostIt(int cellId);

    boolean isBoardTitleUnique(String boardTitle);


}
