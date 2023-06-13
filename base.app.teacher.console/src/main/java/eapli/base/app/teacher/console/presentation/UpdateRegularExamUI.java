package eapli.base.app.teacher.console.presentation;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import eapli.base.exam.application.UpdateRegularExamController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.io.ConsoleUtils;

public class UpdateRegularExamUI extends AbstractUI {

    private UpdateRegularExamController ctrl;

    public UpdateRegularExamUI() {
        ctrl = new UpdateRegularExamController();
    }

    @Override
    protected boolean doShow() {
        LocalDateTime openDate, closeDate;
        var widgetCourse = new SelectWidget<>("Choose a course to update a exam:", ctrl.listCourses());
        widgetCourse.show();

        if (widgetCourse.selectedOption() <= 0)
            return false;
        var chosenCourse = widgetCourse.selectedElement();

        var widgetExam = new SelectWidget<>("Choose an exam from the list of exams:", ctrl.listExams(chosenCourse));
        widgetExam.show();

        if (widgetExam.selectedOption() <= 0)
            return false;
        var chosenExam = widgetExam.selectedElement();

        boolean created = false;

        List<String> lst = new ArrayList<>();
        lst.add("Update regular exam date");
        lst.add("Update regular exam specification");

        do {
            var option = new SelectWidget<>("Choose one of the following options:", lst);
            option.show();

            if (option.selectedOption() <= 0)
                return false;

            if (option.selectedOption() == 1) {

                Optional<LocalDateTime> opt;
                do {
                    opt = ConsoleUtils.readLocalDateTime("New Open date (dd/MM/yyyy HH:mm)");
                } while (opt.isEmpty());
                openDate = opt.get();

                if (openDate.isBefore(LocalDateTime.now())) {
                    System.out.println("Open date must be after current date");
                    return false;
                }

                do {
                    opt = ConsoleUtils.readLocalDateTime("New Close date (dd/MM/yyyy HH:mm)");
                } while (opt.isEmpty());
                closeDate = opt.get();

                if (closeDate.isBefore(openDate)) {
                    System.out.println("Close date must be after open date");
                    return false;
                }

                ctrl.updateRegularExamDate(chosenExam, openDate, closeDate);
                System.out.println("Regular Exam updated with success");
            } else if (option.selectedOption() == 2) {
                var filePath = Console.readLine("New Regular Exam Specification file path: ");

                var file = new File(filePath);
                if (file == null || !file.exists() || !file.canRead()) {
                    System.out.println("File does not exist or does not have read permitions");
                    return false;
                }

                try {
                    if (this.ctrl.updateRegularExamSpecification(chosenExam, file))
                        System.out.println("Regular exam updated with success");
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

            }

            String op = Console.readLine("Do you want to update anything else?(yes/no)");
            if (op.compareToIgnoreCase("no") == 0)
                created = true;
        } while (!created);
        return false;
    }

    @Override
    public String headline() {
        return "Update Regular Exam";
    }

}
