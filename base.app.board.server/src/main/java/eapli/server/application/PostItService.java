package eapli.server.application;

import eapli.base.board.domain.Board;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

public class PostItService {

    public PostItService() {

    }

    public boolean updatePostIt(Board board, int row, int col, String text, SystemUser postItOwner) {
        var c = board.getCell(row, col);
        if (!c.hasPostIt() || !c.getPostIt().getOwner().equals(postItOwner))
            return false;

        return c.changePostItData(board, text);
    }

    // TODO: MOVE VERIFICATIONS TO HERE
    public boolean movePostIt(Board board, int rowFrom, int colFrom, int rowTo, int colTo, SystemUser postItOwner) {
        var cellFrom = board.getCell(rowFrom, colFrom);
        if (!cellFrom.hasPostIt() || !cellFrom.getPostIt().getOwner().equals(postItOwner))
            return false;

        var cellTo = board.getCell(rowTo, colTo);
        if (cellTo.hasPostIt())
            return false;

        return board.movePostIt(rowFrom, colFrom, rowTo, colTo);
    }

}
