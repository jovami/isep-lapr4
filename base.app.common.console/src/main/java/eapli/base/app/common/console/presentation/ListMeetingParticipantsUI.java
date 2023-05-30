package eapli.base.app.common.console.presentation;

import eapli.base.event.meeting.application.ListMeetingParticipantsController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListWidget;
import eapli.framework.presentation.console.SelectWidget;

/**
 * ListMeetingParticipantsUI
 */
public final class ListMeetingParticipantsUI extends AbstractUI {

    private final ListMeetingParticipantsController ctrl;

    public ListMeetingParticipantsUI() {
        super();
        this.ctrl = new ListMeetingParticipantsController();
    }

    @Override
    protected boolean doShow() {
        var widget = new SelectWidget<>("Choose a meeting:", this.ctrl.meetingsOfUser());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;

        var meeting = widget.selectedElement();

        var participants = this.ctrl.meetingParticipants(meeting);

        System.out.printf("\nMeeting: %s\n\n", meeting);
        new ListWidget<>("Participants: ", participants)
                .show();

        return false;
    }

    @Override
    public String headline() {
        return "List Meeting participants";
    }

}
