package eapli.base.app.teacher.console.presentation;

import eapli.base.enrollment.domain.Enrollment;
import eapli.base.event.lecture.application.UpdateScheduleLectureController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UpdateScheduleLectureUI extends AbstractUI {

    private UpdateScheduleLectureController ctrl;

    public UpdateScheduleLectureUI(){
        ctrl = new UpdateScheduleLectureController();

    }

    @Override
    protected boolean doShow() {

        var widgetLecture = new SelectWidget<>("Choose one of the following options:", ctrl.listOfLecturesTaughtByTeacher());
        widgetLecture.show();

        if (widgetLecture.selectedOption() <= 0)
            return false;
        var chosenLecture = widgetLecture.selectedElement();

        LocalDate removedDate = readDate("Date of lecture to be changed");
        LocalDate newDate = readDate("Scheduling for");
        LocalTime time = readTime("The Lecture will start at:");
        int duration;

        do {
            duration = Console.readInteger("Lecture duration: (Minutes)");
        } while (duration < 10);

       var lecture = ctrl.updateDateOfLecture(chosenLecture,removedDate,newDate, time,duration);

       if(lecture.isPresent())
       {
           System.out.println("Lecture updated with success");
           System.out.println(lecture.get().toString());
       }
       else
            System.out.println("There was a problem with the specified parameters");

        return false;
    }


    public LocalDate readDate(final String prompt) {
        System.out.println(prompt);
        do {
            try {
                final int day = Console.readInteger("Day:");
                final int month = Console.readInteger("Month:");
                final int year = Console.readInteger("Year:");
                return LocalDate.of(year,month,day);

            } catch (@SuppressWarnings("unused") final DateTimeException ex) {
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
                return  LocalTime.of(hour,minute);

            } catch (@SuppressWarnings("unused") final DateTimeException ex) {
                System.out.println("There was an error while parsing the given time");
            }
        } while (true);
    }

    @Override
    public String headline() {
        return "Updated Schedule a Lecture";
    }

}
