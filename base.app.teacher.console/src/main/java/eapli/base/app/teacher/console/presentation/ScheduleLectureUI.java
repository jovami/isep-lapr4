package eapli.base.app.teacher.console.presentation;

import eapli.base.enrollment.domain.Enrollment;
import eapli.base.event.lecture.application.ScheduleLectureController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.io.ConsoleUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class ScheduleLectureUI extends AbstractUI {

    private ScheduleLectureController ctrl;

    public ScheduleLectureUI() {
        ctrl = new ScheduleLectureController();
    }

    @Override
    protected boolean doShow() {
        LocalDateTime dateTime;
        LocalDate startDate, endDate;
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
            System.out.println("A lecture cannot be scheduled for a past date");
            return false;
        }
        startDate = dateTime.toLocalDate();
        time = dateTime.toLocalTime();

        Optional<LocalDate> optEndDate;
        do {
            optEndDate = ConsoleUtils.readLocalDate("Scheduling until: (dd/mm/yyyy)");
        } while (optEndDate.isEmpty());
        endDate = optEndDate.get();

        if (startDate.isAfter(endDate)) {
            System.out.println("The start date cannot be after the end date");
            return false;
        }

        int duration;

        do {
            duration = Console.readInteger("Lecture duration: (Minutes)");
        } while (duration < 10);

        Iterable<Enrollment> enrolled = ctrl.enrollmentsByCourse(course);

        if (ctrl.schedule(startDate, endDate, time, duration, enrolled)) {
            System.out.println("Lecture scheduled with success");
        } else {
            System.out.println("There was a problem with the specified parameters");
        }

        return true;
    }

    @Override
    public String headline() {
        return "Scheduling a Lecture";
    }
}
