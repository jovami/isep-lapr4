package eapli.base.app.common.console.presentation.authz;

import eapli.base.board.application.CreatePostItOnBoardController;
import eapli.base.board.application.ShareBoardController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

public class CreatePostItUI extends AbstractUI {

    private final CreatePostItOnBoardController controller;

    public CreatePostItUI() {
        super();
        this.controller = new CreatePostItOnBoardController();
    }

    @Override
    protected boolean doShow() {

        /*var widget = new SelectWidget<>("Choose a board to create a postIt on:", this.controller.listBoardUserLoggedParticipates());
        widget.show();*/


        return false;
    }

    @Override
    public String headline() {
        return "Create PostIt on Board";
    }


}
