package eapli.base.app.manager.console.presentation;

import eapli.base.enrollment.application.OpenCloseEnrollmentController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

public class OpenEnrollmentUI extends AbstractUI {

    private final OpenCloseEnrollmentController ctrl;

    public OpenEnrollmentUI() {
        super();
        this.ctrl = new OpenCloseEnrollmentController();
    }

    @Override
    protected boolean doShow() {
        var widget = new SelectWidget<>("Choose a course to open is enrollments:",
                this.ctrl.openableToEnrollmentsCourses());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;
        var chosen = widget.selectedElement();

        System.out.printf("You chose: %s\n", chosen);
        if (Console.readBoolean("Is this ok? (y/n)")) {
            this.ctrl.openEnrollments(chosen);
            System.out.printf("Enrollments opened with success! \n");
        } else {
            System.out.println("Operation canceled with success!");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Open a enrollment";
    }
}
