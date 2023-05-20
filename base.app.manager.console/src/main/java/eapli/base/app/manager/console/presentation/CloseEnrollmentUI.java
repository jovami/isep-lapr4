package eapli.base.app.manager.console.presentation;

import eapli.base.enrollment.application.OpenCloseEnrollmentController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

public class CloseEnrollmentUI extends AbstractUI {

    private final OpenCloseEnrollmentController ctrl;

    public CloseEnrollmentUI() {
        super();
        this.ctrl = new OpenCloseEnrollmentController();
    }

    @Override
    protected boolean doShow() {
        var widget = new SelectWidget<>("Choose a course to close is enrollments:", this.ctrl.enrollableCourses());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;
        var chosen = widget.selectedElement();

        System.out.printf("You chose: %s\n", chosen);
        if (Console.readBoolean("Is this ok? (y/n)")) {
            this.ctrl.closeEnrollments(chosen);
            System.out.printf("Enrollments closed with success!\n");
        } else {
            System.out.println("Operation canceled with success!");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Close a enrollment";
    }
}
