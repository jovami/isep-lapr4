package eapli.base.app.common.console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import eapli.base.clientusermanagement.dto.SystemUserNameEmailDTO;
import eapli.base.event.meeting.application.ScheduleMeetingController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.io.ConsoleUtils;

public class ScheduleMeetingUI extends AbstractUI {

    private ScheduleMeetingController ctrl;

    public ScheduleMeetingUI() {
        ctrl = new ScheduleMeetingController();
    }

    @Override
    protected boolean doShow() {
        LocalDateTime dateTime;
        LocalDate date;
        LocalTime time;

        String description = Console.readLine("Meeting subject");

        Optional<LocalDateTime> optDateTime;
        do {
            optDateTime = ConsoleUtils.readLocalDateTime("Scheduling for: (dd/mm/yyyy hh:mm)");
        } while (optDateTime.isEmpty());
        dateTime = optDateTime.get();

        if (dateTime.isBefore(LocalDateTime.now())){
            System.out.println("A meeting cannot be scheduled for a past date");
            return false;
        }
        date = dateTime.toLocalDate();
        time = dateTime.toLocalTime();

        int duration;
        do {
            duration = Console.readInteger("Meeting duration: (Minutes)");
        } while (duration < 10);

        if (!ctrl.createMeeting(description, date, time, duration)) {
            System.out.println("There was a problem with the specified parameters");
        }

        boolean invite = true;
        // INVITE users
        try {

            do {
                SelectWidget<SystemUserNameEmailDTO> opt = new SelectWidget<>("Choose User", ctrl.Users());

                opt.show();

                if (opt.selectedElement() != null) {
                    if (!ctrl.invite(opt.selectedElement())) {
                        System.out.println("\n\tThis user is already invited\n");
                    }
                } else {
                    invite = false;
                }
            } while (invite);

        } catch (ConcurrencyException e) {
            System.out.println(e.getMessage());
        }

        if (ctrl.schedule()) {
            System.out.println("Meeting scheduled with success");
        } else {
            System.out.println(
                    "There was a problem scheduling a meeting:\n\tSome of the user may have another shcedule for the given time");
        }
        return true;

    }

    @Override
    public String headline() {
        return "Scheduling a meeting";
    }
}
