package eapli.base.app.teacher.console.presentation;

import eapli.base.exam.aplication.CreateRegularExamController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListWidget;
import eapli.framework.presentation.console.SelectWidget;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class CreateRegularExamUI extends AbstractUI {

    private CreateRegularExamController ctrl;
    public CreateRegularExamUI(){
        ctrl = new CreateRegularExamController();
    }

    @Override
    protected boolean doShow() {


        Date openDate,closeDate;
        var widget = new SelectWidget<>("Choose a course to create a exam:", ctrl.listCoursesTeacherTeaches());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;
        var chosen = widget.selectedElement();

        var filePath = Console.readLine("Regular Exam Specification file path: ");

        var file = new File(filePath);
        if (file == null || !file.exists() || !file.canRead()) {
            System.out.println("File does not exist or does not have read permitions");
            return false;
        }

        try {
            openDate = Console.readDate("Open date(dd/MM/yyyy HH:mm)","dd/MM/yyyy HH:mm");
            closeDate = Console.readDate("Close date(dd/MM/yyyy HH:mm)","dd/MM/yyyy HH:mm");

            if (this.ctrl.createRegularExam(file,openDate,closeDate,chosen))
                System.out.println("Regular exam created with success");
            else
                System.out.println("Error parsing the Specification file");
        } catch (IOException e) {
            System.out.println("Error reading file contents");
        } catch (IntegrityViolationException e) {
            System.out.println("Integrity violation");
        } catch (ConcurrencyException e) {
            System.out.println(
                    "Unfortunatelly there was an unexpected error in the application.\n" +
                            "Please try again and if the problem persists, contact your system admnistrator.");
        }

        return false;

    }

    @Override
    public String headline() {
        return "Create Regular Exam";
    }

}
