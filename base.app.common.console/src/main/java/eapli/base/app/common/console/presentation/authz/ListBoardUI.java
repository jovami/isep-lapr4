package eapli.base.app.common.console.presentation.authz;

import eapli.base.board.application.ListBoardController;
import eapli.base.board.domain.Board;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

public class ListBoardUI extends AbstractListUI<Board> {

    private final ListBoardController controller;

    public ListBoardUI() {
        super();
        this.controller = new ListBoardController();
    }

    @Override
    public String headline() {
        return "List boards";
    }

    @Override
    protected String elementName() {
        return "Board";
    }

    @Override
    protected Visitor<Board> elementPrinter() {
        return System.out::println;
    }

    @Override
    protected Iterable<Board> elements() {
        return this.controller.listBoards();
    }

    @Override
    protected String emptyMessage() {
        return "No boards";
    }

    @Override
    protected String listHeader() {
        return "Available boards:";
    }
}
