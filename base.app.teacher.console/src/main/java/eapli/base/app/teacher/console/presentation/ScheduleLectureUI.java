package eapli.base.app.teacher.console.presentation;

import eapli.base.clientusermanagement.dto.StudentUsernameMecanographicNumberDTO;
import eapli.base.clientusermanagement.dto.SystemUserNameEmailDTO;
import eapli.base.event.lecture.application.ScheduleLectureController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;


public class ScheduleLectureUI extends AbstractUI {

    private ScheduleLectureController ctrl;

    public ScheduleLectureUI(){
        ctrl = new ScheduleLectureController();

    }

    @Override
    protected boolean doShow() {


        LocalDate startDate = readDate("Scheduling for");
        LocalDate endDate = readDate("Scheduling until");
        LocalTime time = readTime("The Lecture will start at:");
        int duration;
        do{
            duration=Console.readInteger("Lecture duration: (Minutes)");
        }while (duration<10);

        if(!ctrl.createLecture(startDate,endDate,time,duration)){
            System.out.println("There was a problem with the specified parameters");
        }

        boolean invite = true;
        //INVITE
        try {

        do{
            SelectWidget<StudentUsernameMecanographicNumberDTO> opt = new SelectWidget<>("Choose User",ctrl.students());

            opt.show();

            if(opt.selectedElement()!=null){
                if(!ctrl.inviteStudent(opt.selectedElement())){
                    System.out.println("\n\tThis user is already invited\n");
                }
            }else {
                invite = false;
            }
        }while (invite);

        }catch (ConcurrencyException e){
            System.out.println(e.getMessage());
        }

        if(ctrl.schedule()){
            System.out.println("Lecture scheduled with success");
        }else {
            System.out.println("There was a problem while scheduling the lecture, Teacher not available");
        }
        return true;

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
        return "Scheduling a Lecture";
    }
}
