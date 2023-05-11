package eapli.base.app.manager.console.presentation;

import eapli.base.course.application.CreateCourseController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class CreateCourseUI extends AbstractUI {
    @Autowired
    private CreateCourseController ctrl;
    public CreateCourseUI(){
        ctrl = new CreateCourseController();
    }

    @Override
    protected boolean doShow() {

        String name,description;
        Date startDate,endDate;
        boolean created=false;

        do {
            name = Console.readNonEmptyLine("Course name:", "Value can't be null");
            description = Console.readNonEmptyLine("Course description:", "Value can't be null");

            startDate = Console.readDate("Start date(DD/MM/YYYY)","dd/MM/yyyy");
            endDate = Console.readDate("End date(DD/MM/YYYY)","dd/MM/yyyy");

            if(ctrl.createCourse(name,description,startDate,endDate)){
                created=true;
            }else {
                System.out.println("There was a error creating the specified course");
            }

        }while (!created);

        boolean setCapacity = Console.readBoolean("Do you wish to set course capacity?(s/n)");

        int minCapacity,maxCapacity;
        boolean addedCapacity=false;

        if(setCapacity) {
            do {
                    minCapacity = Console.readInteger("Min capacity");
                    maxCapacity = Console.readInteger("Max capacity");
                    addedCapacity=ctrl.addCapacity(minCapacity,maxCapacity);

                    if (!addedCapacity){
                        System.out.println("Capacities do not meet the requirements");
                    }
            }while (!addedCapacity);
        }

        if (ctrl.saveCourse()){
            System.out.println("\n\n\tCourse created and saved with success\n:"+ctrl.courseString());
        }
        System.out.println(ctrl.countAll());
        return false;

    }

    @Override
    public String headline() {
        return "Create Course";
    }
}
