package eapli.base.app.teacher.console.presentation;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import eapli.base.exam.application.CreateRegularExamController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

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
            System.out.println("File does not exist or does not have read permitions");
            return false;
        }

        var fmt = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

        try {

            Optional<LocalDateTime> opt;
            do {
                opt = readDate("Open date(dd/MM/yyyy HH:mm)", fmt);
            } while (opt.isEmpty());
            openDate = opt.get();

            do {
                opt = readDate("Close date(dd/MM/yyyy HH:mm)", fmt);
            } while (opt.isEmpty());
            closeDate = opt.get();

            if (this.ctrl.createRegularExam(file, openDate, closeDate, chosen))
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
                            "Please try again and if the problem persists, contact your system admnistrator.");
        }
        return false;
    }

    private Optional<LocalDateTime> readDate(String prompt, DateTimeFormatter fmt) {
        try {
            var line = Console.readLine(prompt);
            return Optional.of(LocalDateTime.parse(line, fmt));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    @Override
    public String headline() {
        return "Create Regular Exam";
    }

}
