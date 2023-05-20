package eapli.base.board.application;

import eapli.base.board.domain.Board;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;

@UseCaseController
public class ListBoardController {

    private final BoardRepository repository;

    public ListBoardController() {
        repository = PersistenceContext.repositories().boards();
    }

    public Iterable<Board> listBoards() {
        return this.repository.findAll();
    }
}
