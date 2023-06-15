package eapli.base.app.teacher.console.presentation;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.base.formativeexam.application.CreateFormativeExamController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

/**
 * CreateFormativeExamUI
 */
public class CreateFormativeExamUI extends AbstractUI {
    private static final Logger logger = LogManager.getLogger(CreateFormativeExamUI.class);

    private final CreateFormativeExamController ctrl = new CreateFormativeExamController();

    @Override
    protected boolean doShow() {
        var widget = new SelectWidget<>("Choose a course to add the Formative Exam to:", this.ctrl.courses());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;
        var chosen = widget.selectedElement();

        System.out.println();

        var filePath = Console.readLine("Formative Exam Specification file path: ");

        var file = new File(filePath);
        if (file == null || !file.exists() || !file.canRead()) {
            System.out.println("File does not exist or does not have read permitions");
            return false;
        }

        var title = Console.readLine("Formative Exam title: ");
        if (title == null || title.isEmpty()) {
            System.out.println("Title can't be empty");
            return false;
        }

        try {
            if (this.ctrl.createFormativeExam(title, chosen, file))
                System.out.println("Formative exam created with success");
            else
                System.out.println("Error parsing the Specification file");
        } catch (IOException e) {
            System.out.println("Error reading file contents");
        } catch (IntegrityViolationException e) {
            System.out.println("Integrity violation");
        } catch (ConcurrencyException e) {
            logger.error("This should've never happened; yet it did :^)", e);
            System.out.println(
                    "Unfortunatelly there was an unexpected error in the application.\n" +
                            "Please try again and if the problem persists, contact your system admnistrator.");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Create Formative Exam";
    }
}
