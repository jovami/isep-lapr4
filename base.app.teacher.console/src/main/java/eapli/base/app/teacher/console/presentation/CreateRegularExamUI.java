package eapli.base.app.teacher.console.presentation;

import eapli.base.exam.aplication.CreateRegularExamController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class CreateRegularExamUI extends AbstractUI {

    private CreateRegularExamController ctrl;
    public CreateRegularExamUI(){
        ctrl = new CreateRegularExamController();
    }

    @Override
    protected boolean doShow() {

        String examTitle, examHeader, headerDescription;
        Date openDate,closeDate;
        boolean created=false;

        do {
            examTitle = Console.readNonEmptyLine("Exam title:", "Value can't be null");
            examHeader = Console.readNonEmptyLine("Exam header:", "Value can't be null");
            headerDescription = Console.readNonEmptyLine("Header description:", "Value can't be null");

            openDate = Console.readDate("Open date(DD/MM/YYYY)","dd/MM/yyyy");
            closeDate = Console.readDate("Close date(DD/MM/YYYY)","dd/MM/yyyy");

            if(ctrl.createRegularExam(examTitle,examHeader,headerDescription,openDate,closeDate)){
                created=true;
            }else {
                System.out.println("There was a error creating the specified course");
            }

        }while (!created);

        if (ctrl.saveRegularExam()){
            System.out.println("\n\n\tRegular Exam created and saved with success\n:"+ctrl.regularExamString());
        }
        return false;

    }

    @Override
    public String headline() {
        return "Create Regular Exam";
    }

}
