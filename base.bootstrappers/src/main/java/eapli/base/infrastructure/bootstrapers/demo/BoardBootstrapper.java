package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.board.domain.domain.Board;
import eapli.base.board.domain.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.actions.Action;

public class BoardBootstrapper implements Action {


    @Override
    public boolean execute() {
        // some users that signup and are approved
        sabeBoard("tob", 10, 10);
        sabeBoard("quim",  1, 2);
        sabeBoard("toni", 9, 4);

        return true;
    }

    private void sabeBoard(String name, int rows, int columns) {
        BoardRepository repo = PersistenceContext.repositories().boards();
        Board board = new Board(name,rows,columns);
        repo.save(board);
    }


}
