package eapli.base.app.common.console;

import eapli.base.event.meeting.application.AcceptRejectMeetingRequestController;
import eapli.base.event.meeting.dto.MeetingDTO;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

public class AcceptRejectMeetingRequestUI extends AbstractUI {
    private AcceptRejectMeetingRequestController ctrl;

    public AcceptRejectMeetingRequestUI() {
        ctrl = new AcceptRejectMeetingRequestController();
    }

    @Override
    protected boolean doShow() {

        try {
            SelectWidget<MeetingDTO> opt;

            do {
                Iterable<MeetingDTO> iterable = ctrl.showInvitedMeetings();
                if (!iterable.iterator().hasNext()) {
                    System.out.println("There is no meetings to show");
                    return false;
                }

                opt = new SelectWidget<>("\nChoose Meeting", iterable);
                opt.show();
                if (opt.selectedElement() == null) {
                    break;
                }

                System.out.println("\nChoose an option:");
                System.out.println("1. Accept Meeting Request");
                System.out.println("2. Refuse Meeting Request\n");
                System.out.println("0. Exit");

                final int option = Console.readOption(1, 2, 0);
                try {


                    switch (option) {
                        case 1:
                            if (this.ctrl.acceptMeetingRequest(opt.selectedElement()))
                                System.out.println("Meeting accepted!");
                            break;
                        case 2:
                            if (this.ctrl.rejectMeetingRequest(opt.selectedElement()))
                                System.out.println("Meeting rejected!");
                            break;
                        case 0:
                            System.out.println("Return to previous menu");
                            break;
                        default:
                            System.out.println("Invalid option");
                            break;
                    }

                } catch (IntegrityViolationException | ConcurrencyException ex) {
                    LOGGER.error("Error performing the operation", ex);
                    System.out.println(
                            "Unfortunatelly there was an unexpected error in the application. Please try again and if the problem persists, contact your system admnistrator.");
                }

            } while (opt.selectedElement() != null);

        } catch (ConcurrencyException e) {
            System.out.println(e.getMessage());
        }


        return true;

    }

    @Override
    public String headline() {
        return "Accept/Reject a Meeting Request";
    }
}
