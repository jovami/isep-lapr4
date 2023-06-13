package eapli.base.app.teacher.console.presentation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import eapli.base.event.lecture.application.UpdateScheduleLectureController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.io.ConsoleUtils;

public class UpdateScheduleLectureUI extends AbstractUI {

    private UpdateScheduleLectureController ctrl;

    public UpdateScheduleLectureUI() {
        this.ctrl = new UpdateScheduleLectureController();
    }

    @Override
    protected boolean doShow() {
        LocalDateTime dateTime;
        LocalDate removedDate, newDate;
        LocalTime time;
        var widgetLecture = new SelectWidget<>("Choose one of the following options:",
                ctrl.listOfLecturesTaughtByTeacher());
        widgetLecture.show();

        if (widgetLecture.selectedOption() <= 0)
            return false;
        var chosenLecture = widgetLecture.selectedElement();

        Optional<LocalDate> optRemovedDate;
        do {
            optRemovedDate = ConsoleUtils.readLocalDate("Date of lecture to be changed: (dd/mm/yyyy)");
        } while (optRemovedDate.isEmpty());
        removedDate = optRemovedDate.get();

        Optional<LocalDateTime> optDateTime;
        do {
            optDateTime = ConsoleUtils.readLocalDateTime("Scheduling for: (dd/mm/yyyy hh:mm)");
        } while (optDateTime.isEmpty());
        dateTime = optDateTime.get();

        if (dateTime.isBefore(LocalDateTime.now())) {
            System.out.println("A lecture cannot be scheduled for a past date");
            return false;
        }
        newDate = dateTime.toLocalDate();
        time = dateTime.toLocalTime();

        int duration;
        do {
            duration = Console.readInteger("Lecture duration: (Minutes)");
        } while (duration < 10);

        var lecture = ctrl.updateDateOfLecture(chosenLecture, removedDate, newDate, time, duration);

        if (lecture.isPresent()) {
            System.out.println("Lecture updated with success");
            System.out.println(lecture.get().toString());
        } else
            System.out.println("There was a problem with the specified parameters");

        return false;
    }

    @Override
    public String headline() {
        return "Updated Schedule a Lecture";
    }

}
