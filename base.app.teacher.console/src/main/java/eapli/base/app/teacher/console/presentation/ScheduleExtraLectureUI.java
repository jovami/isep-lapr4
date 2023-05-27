package eapli.base.app.teacher.console.presentation;

import eapli.base.clientusermanagement.dto.StudentUsernameMecanographicNumberDTO;
import eapli.base.event.lecture.application.ScheduleExtraLectureController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.io.ConsoleUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class ScheduleExtraLectureUI extends AbstractUI {

    private final ScheduleExtraLectureController ctrl;

    public ScheduleExtraLectureUI() {
        ctrl = new ScheduleExtraLectureController();
    }

    @Override
    protected boolean doShow() {
        int duration;
        LocalDateTime dateTime;
        LocalDate date;
        LocalTime time;

        var option = new SelectWidget<>("Choose Course", ctrl.getCourses());
        option.show();
        var course = option.selectedElement();

        Optional<LocalDateTime> optDateTime;
        do {
            optDateTime = ConsoleUtils.readLocalDateTime("Scheduling for: (dd/mm/yyyy hh:mm)");
        } while (optDateTime.isEmpty());
        dateTime = optDateTime.get();

        if (dateTime.isBefore(LocalDateTime.now())) {
            System.out.println("A lecture can't be scheduled for a past date");
            return false;
        }
        date = dateTime.toLocalDate();
        time = dateTime.toLocalTime();

        do {
            duration = Console.readInteger("Extraordinary lecture duration: (Minutes)");
        } while (duration < 10);

        var list = ctrl.listStudents(course);
        var participants = new ArrayList<StudentUsernameMecanographicNumberDTO>();

        boolean invite = true;

        do {
            var widget = new SelectWidget<>("Choose User:", list);
            widget.show();

            if (widget.selectedOption() != 0) {
                participants.add(list.remove(widget.selectedOption() - 1));
            } else {
                invite = false;
            }
        } while (invite);


        if (ctrl.schedule(date, time, duration, participants)) {
            System.out.println("Extraordinary lecture scheduled with success");
        } else {
            System.out.println("There was a problem while scheduling the extraordinary lecture");
        }

        return true;
    }

    @Override
    public String headline() {
        return "Scheduling an Extraordinary lecture";
    }
}
