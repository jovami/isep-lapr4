package eapli.base.app.common.console;

import eapli.base.event.meeting.application.CancelMeetingController;
import eapli.base.event.meeting.dto.MeetingDTO;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.util.List;

public class CancelMeetingUI extends AbstractUI {
    private final CancelMeetingController ctrl = new CancelMeetingController();

    public CancelMeetingUI() {
    }

    @Override
    protected boolean doShow() {
        SelectWidget<MeetingDTO> opt;
        List<MeetingDTO> meetingsDto;
        do {
            meetingsDto = ctrl.meetings();
            if (meetingsDto.isEmpty()) {
                System.out.println("\n\t You have no more meetings scheduled");
                break;
            }
            opt = new SelectWidget<>("Choose Meeting\n", meetingsDto);
            opt.show();
            if (opt.selectedElement() != null) {
                if (ctrl.cancel(opt.selectedElement())) {
                    System.out.println("\n\t Meeting canceled with success");
                } else {
                    System.out.println("\n\t There was problem cancelling the meeting, try again later");
                }
            }
            System.out.println(SEPARATOR);

        } while (opt.selectedElement() != null);

        return true;
    }

    @Override
    public String headline() {
        return "Canceling a meeting";
    }
}
