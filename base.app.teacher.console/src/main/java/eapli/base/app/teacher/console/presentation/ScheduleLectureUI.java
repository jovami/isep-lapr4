package eapli.base.app.teacher.console.presentation;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

import eapli.base.course.domain.Course;
import eapli.base.enrollment.domain.Enrollment;
import eapli.base.event.lecture.application.ScheduleLectureController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

public class ScheduleLectureUI extends AbstractUI {

    private ScheduleLectureController ctrl;

    public ScheduleLectureUI() {
        ctrl = new ScheduleLectureController();
    }

    @Override
    protected boolean doShow() {
        SelectWidget<Course> option = new SelectWidget<>("Choose Course",
                ctrl.coursesTaughtBy(ctrl.getSessionTeacher()));
        option.show();
        option.selectedElement();

        LocalDate startDate = readDate("Scheduling for");
        LocalDate endDate = readDate("Scheduling until");
        LocalTime time = readTime("The Lecture will start at:");
        int duration;

        do {
            duration = Console.readInteger("Lecture duration: (Minutes)");
        } while (duration < 10);

        if (!ctrl.createLecture(startDate, endDate, time, duration))
            System.out.println("There was a problem with the specified parameters");

        Iterable<Enrollment> enrolled = ctrl.enrollmentsByCourse(option.selectedElement());

        if (ctrl.schedule(enrolled))
            System.out.println("Lecture scheduled with success");
        else
            System.out.println("There was a problem while scheduling the lecture, Teacher not available");

        return true;

    }

    public LocalDate readDate(final String prompt) {
        System.out.println(prompt);

        do {
            try {
                final int day = Console.readInteger("Day:");
                final int month = Console.readInteger("Month:");
                final int year = Console.readInteger("Year:");
                return LocalDate.of(year, month, day);
            } catch (final DateTimeException ex) {
                System.out.println("There was an error while parsing the given date");
            }
        } while (true);
    }

    public LocalTime readTime(final String prompt) {
        System.out.println(prompt);

        do {
            try {
                final int hour = Console.readInteger("Hour:");
                final int minute = Console.readInteger("Minute:");
                return LocalTime.of(hour, minute);
            } catch (final DateTimeException ex) {
                System.out.println("There was an error while parsing the given time");
            }
        } while (true);
    }

    @Override
    public String headline() {
        return "Scheduling a Lecture";
    }
}
