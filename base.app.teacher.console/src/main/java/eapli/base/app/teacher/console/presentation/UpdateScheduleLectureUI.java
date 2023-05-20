package eapli.base.app.teacher.console.presentation;

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

        List<String> lst = new ArrayList<>();
        lst.add("Update date of lecture");
        lst.add("Update starting time of lecture");
        lst.add("Update duration of lecture");

        boolean created = false;
        do{
            var option = new SelectWidget<>("Choose one of the following options:", lst);
            option.show();

            if (option.selectedOption() <= 0)
                return false;

            if (option.selectedOption() == 1)
            {
                System.out.println("New date for lecture\n");
                LocalDate startDate = readDate("Scheduling for");
                LocalDate endDate = readDate("Scheduling until");

                if(!ctrl.updateDateOfLecture(chosenLecture,startDate,endDate))
                    System.out.println("There was a problem with the specified parameters");
                else
                    System.out.println("Lecture date was successfully updated");


            }else if(option.selectedOption() == 2)
            {
                System.out.println("New starting time for lecture\n");
                LocalTime time = readTime("The Lecture will start at:");

                if(!ctrl.updateStartingTimeOfLecture(chosenLecture,time))
                    System.out.println("There was a problem with the specified parameters");
                else
                    System.out.println("Lecture starting time was successfully updated");


            }else if(option.selectedOption() == 3)
            {
                System.out.println("New duration of Lecture\n");
                int duration;
                do{
                    duration=Console.readInteger("Lecture duration: (Minutes)");
                }while (duration<10);

                if(!ctrl.updateDurationOfLecture(chosenLecture,duration))
                    System.out.println("There was a problem with the specified parameters");
                else
                    System.out.println("Lecture duration was successfully updated");

            }


            String op = Console.readLine("Do you want to update anything else?(yes/no)");
            if (op.compareToIgnoreCase("no") == 0)
                created = true;

        }while (!created);

        System.out.println(ctrl.showLecture());

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
