package eapli.base.app.manager.console.presentation;

import eapli.base.course.application.OpenCloseCourseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

/**
 * OpenCourseUI
 */
public class OpenCourseUI extends AbstractUI {
    private final OpenCloseCourseController ctrl;

    public OpenCourseUI() {
        super();
        this.ctrl = new OpenCloseCourseController();
    }

    @Override
    protected boolean doShow() {
        var widget = new SelectWidget<>("Choose a course to open:", this.ctrl.openableCourses());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;
        var chosen = widget.selectedElement();

        System.out.printf("You chose: %s\n", chosen);
        if (Console.readBoolean("Is this ok? (y/n)")) {
            try {
                this.ctrl.openCourse(chosen).consume(
                        // Error
                        System.out::println,
                        // Ok
                        (state) -> System.out.printf("Course opened with success! (previous state was %s)\n", state));
            } catch (ConcurrencyException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Operation canceled with success!");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Open a course";
    }
}
