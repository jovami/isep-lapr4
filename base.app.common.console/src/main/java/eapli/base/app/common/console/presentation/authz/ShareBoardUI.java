package eapli.base.app.common.console.presentation.authz;


import eapli.base.board.application.ShareBoardController;
import eapli.base.clientusermanagement.dto.SystemUserNameEmailDTO;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListWidget;
import eapli.framework.presentation.console.SelectWidget;

import java.util.ArrayList;

public class ShareBoardUI extends AbstractUI {

    private final ShareBoardController controller;

    public ShareBoardUI() {
        super();
        this.controller = new ShareBoardController();
    }

    @Override
    protected boolean doShow() {
        var widget = new SelectWidget<>("Choose a board to share:", this.controller.listBoardsUserOwns());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;
        var chosen = widget.selectedElement();

        var list = controller.Users();
        var participants = new ArrayList<SystemUserNameEmailDTO>();

        boolean invite = true;

        try {

            do {

                var widgetx = new SelectWidget<>("Choose User:", list);
                widgetx.show();

                if (widgetx.selectedOption() != 0) {
                    participants.add(list.remove(widgetx.selectedOption() - 1));
                } else {
                    invite = false;
                }

            } while (invite);

        } catch (ConcurrencyException e) {
            System.out.println(e.getMessage());
        }

        if(controller.shareBoard(chosen,participants)){
            System.out.println("Board shared with success");
        } else {
            System.out.println(
                    "There was a problem sharing a board");
        }

        var participantsx = this.controller.boardParticipants(chosen);

        new ListWidget<>("Participants: ", participantsx)
                .show();

        return false;

    }

    @Override
    public String headline() {
        return "Share board";
    }



}
