package eapli.base.app.common.console;

import eapli.base.clientusermanagement.dto.SystemUserNameEmailDTO;
import eapli.base.event.Meeting.application.ScheduleMeetingController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;


public class ScheduleMeetingUI extends AbstractUI {

    private ScheduleMeetingController ctrl;

    public ScheduleMeetingUI(){
        ctrl = new ScheduleMeetingController();

    }

    @Override
    protected boolean doShow() {
        String description = Console.readLine("Meeting subject");
        LocalDate date = readDate("Scheduling for");
        LocalTime time = readTime("The meeting will start at:");
        int duration;
        do{
            duration=Console.readInteger("Meeting duration:");
        }while (duration<10);

        if(!ctrl.createMeeting(description,date,time,duration)){
            System.out.println("There was a problem with the specified parameters");
        }

        boolean invite = true;
        //INVITE users
        try {

        do{
            SelectWidget<SystemUserNameEmailDTO> opt = new SelectWidget<>("Choose User",ctrl.Users());

            opt.show();

            if(opt.selectedElement()!=null){
                if(!ctrl.invite(opt.selectedElement())){
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
            System.out.println("Meeting scheduled with success");
        }else {
            System.out.println("There was a problem scheduling a meeting:\n\tSome of the user may have another shcedule for the given time");
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
        return "Scheduling a meeting";
    }
}
