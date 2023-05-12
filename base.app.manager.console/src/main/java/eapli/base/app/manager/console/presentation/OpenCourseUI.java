package eapli.base.app.manager.console.presentation;

import eapli.base.course.application.OpenCourseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

/**
 * OpenCourseUI
 */
public class OpenCourseUI extends AbstractUI {
    private final OpenCourseController ctrl;

    public OpenCourseUI() {
        super();
        this.ctrl = new OpenCourseController();
    }

    @Override
    protected boolean doShow() {
        boolean keepGoing = false;

        var widget = new SelectWidget<>("Choose a course to open:", this.ctrl.getCourses());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;
        var chosen = widget.selectedElement();

        System.out.printf("You chose: %s\n", chosen);
        if (Console.readBoolean("Is this ok? (y/n)")) {
            try {
                this.ctrl.openCourse(chosen).consume(
                        System.out::println,
                        (state) -> System.out.printf("Course opened with success! (previous state was %s)\n", state));

                keepGoing = Console.readBoolean("Do you wish to open another course? (y/n)");
            } catch (ConcurrencyException e) {
                System.out.println(e.getMessage());
                keepGoing = false;
            }
        } else {
            keepGoing = !Console.readBoolean("Exit? (y/n)");
        }

        return keepGoing;
    }

    @Override
    public String headline() {
        return "Open a course";
    }
}
