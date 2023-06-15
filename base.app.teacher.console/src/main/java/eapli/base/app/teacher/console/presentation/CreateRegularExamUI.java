package eapli.base.app.teacher.console.presentation;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import eapli.base.exam.application.CreateRegularExamController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.io.ConsoleUtils;

public class CreateRegularExamUI extends AbstractUI {

    private CreateRegularExamController ctrl;

    public CreateRegularExamUI() {
        ctrl = new CreateRegularExamController();
    }

    @Override
    protected boolean doShow() {
        LocalDateTime openDate, closeDate;
        var widget = new SelectWidget<>("Choose a course to create a exam:", ctrl.listCoursesTeacherTeaches());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;
        var chosen = widget.selectedElement();

        var filePath = Console.readLine("Regular Exam Specification file path: ");

        var file = new File(filePath);
        if (file == null || !file.exists() || !file.canRead()) {
            System.out.println("File does not exist or does not have read permissions");
            return false;
        }

        var title = Console.readLine("Exam title: ");
        if (title == null || title.isEmpty()) {
            System.out.println("Exam title can't be empty");
            return false;
        }

        try {
            Optional<LocalDateTime> opt;
            do {
                opt = ConsoleUtils.readLocalDateTime("Open date(dd/MM/yyyy HH:mm)");
            } while (opt.isEmpty());
            openDate = opt.get();

            if (openDate.isBefore(LocalDateTime.now())) {
                System.out.println("Open date must be after current date");
                return false;
            }

            do {
                opt = ConsoleUtils.readLocalDateTime("Close date(dd/MM/yyyy HH:mm)");
            } while (opt.isEmpty());
            closeDate = opt.get();

            if (closeDate.isBefore(openDate)) {
                System.out.println("Close date must be after open date");
                return false;
            }

            if (this.ctrl.createRegularExam(file, title, openDate, closeDate, chosen))
                System.out.println("Regular exam created with success");
            else
                System.out.println("Error parsing the Specification file");
        } catch (IOException e) {
            System.out.println("Error reading file contents");
        } catch (IntegrityViolationException e) {
            System.out.println("Integrity violation");
        } catch (ConcurrencyException e) {
            System.out.println(
                    "Unfortunately there was an unexpected error in the application.\n" +
                            "Please try again and if the problem persists, contact your system administrator.");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Create Regular Exam";
    }

}
