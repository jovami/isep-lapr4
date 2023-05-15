package eapli.base.app.common.console.presentation.authz;

import eapli.base.board.application.ListBoardController;
import eapli.framework.presentation.console.AbstractUI;
import org.springframework.beans.factory.annotation.Autowired;

public class ListBoardUI extends AbstractUI {


    @Autowired
    ListBoardController controller;


    public ListBoardUI() {
        controller = new ListBoardController();
    }


    @Override
    protected boolean doShow() {
        controller.listBoards();
        return true;
    }

    @Override
    public String headline() {
        return "Board";
    }


}
