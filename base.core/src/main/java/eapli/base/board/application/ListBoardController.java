package eapli.base.board.application;

import eapli.base.board.domain.Board;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

public class ListBoardController {

    private Board board;

    private final BoardRepository repository;

    public ListBoardController(){
        repository = PersistenceContext.repositories().boards();
    }

    public void listBoards(){
        for (Board board: repository.findAll()) {
            System.out.println(board);
        }
    }
}
