package eapli.base.app.manager.console.presentation;

import java.time.LocalDate;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.base.course.application.CreateCourseController;
import eapli.base.course.dto.CreateCourseDTO;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jovami.util.io.ConsoleUtils;

public final class CreateCourseUI extends AbstractUI {
    private static final Logger logger = LogManager.getLogger(CreateCourseUI.class);

    private final CreateCourseController ctrl;

    public CreateCourseUI() {
        this.ctrl = new CreateCourseController();
    }

    @Override
    protected boolean doShow() {
        Long code;
        String title, description;
        LocalDate startDate, endDate;
        int minCapacity, maxCapacity;

        title = Console.readNonEmptyLine("Course title:", "Title cannot be null");
        code = Console.readLong("Course code:");
        description = Console.readNonEmptyLine("Course description:", "Description cannot be null");

        Optional<LocalDate> opt;
        do {
            opt = ConsoleUtils.readLocalDate("Start date (dd/mm/yyyy)");
        } while (opt.isEmpty());
        startDate = opt.get();

        if (startDate.isBefore(LocalDate.now())) {
            System.out.println("Start date cannot be before today");
            return false;
        }

        do {
            opt = ConsoleUtils.readLocalDate("End date (dd/mm/yyyy)");
        } while (opt.isEmpty());
        endDate = opt.get();

        if (startDate.isAfter(endDate)) {
            System.out.println("Start date cannot be after end date");
            return false;
        }

        minCapacity = Console.readInteger("Minimum course capacity:");
        maxCapacity = Console.readInteger("Maximum course capacity:");

        var dto = new CreateCourseDTO(title, code, description, startDate, endDate, minCapacity, maxCapacity);

        System.out.printf("Course information:\n%s\n", dto);
        if (!Console.readBoolean("Is this ok? (y/n)"))
            return true;

        try {
            this.ctrl.createCourse(dto);
            System.out.println("Course created with success!");
            return true;
        } catch (IntegrityViolationException | IllegalArgumentException e) {
            System.out.println("Integrity rule violated");
        } catch (ConcurrencyException e) {
            logger.error("This should've never happened; yet it did :^)", e);
            System.out.println(
                    "Unfortunately there was an unexpected error in the application.\n" +
                            "Please try again and if the problem persists, contact your system admnistrator.");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Create Course";
    }
}
