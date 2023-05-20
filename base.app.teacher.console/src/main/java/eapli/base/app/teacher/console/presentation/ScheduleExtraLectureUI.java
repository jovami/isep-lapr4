package eapli.base.app.teacher.console.presentation;

import java.time.LocalDate;
import java.time.LocalTime;

import eapli.base.clientusermanagement.dto.StudentUsernameMecanographicNumberDTO;
import eapli.base.event.lecture.application.ScheduleExtraLectureController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

public class ScheduleExtraLectureUI extends AbstractUI {

    private final ScheduleExtraLectureController ctrl;

    public ScheduleExtraLectureUI() {
        ctrl = new ScheduleExtraLectureController();
    }

    @Override
    protected boolean doShow() {
        int duration;
        LocalDate date = ctrl.readDate("Scheduling for");
        LocalTime time = ctrl.readTime("The extraordinary lecture will start at:");

        do {
            duration = Console.readInteger("Extraordinary lecture duration: (Minutes)");
        } while (duration < 10);

        if (!ctrl.createLecture(date, time, duration)) {
            System.out.println("There was a problem with the specified parameters");
        }

        boolean invite = true;
        try {
            do {
                SelectWidget<StudentUsernameMecanographicNumberDTO> opt = new SelectWidget<>("Choose User",
                        ctrl.students());
                opt.show();

                if (opt.selectedElement() != null) {
                    if (!ctrl.inviteStudent(opt.selectedElement())) {
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
            System.out.println("Extraordinary lecture scheduled with success");
        } else {
            System.out.println(
                    "There was a problem while scheduling the extraordinary lecture, teacher is not available");
        }

        return true;
    }

    @Override
    public String headline() {
        return "Scheduling an Extraordinary lecture";
    }
}
