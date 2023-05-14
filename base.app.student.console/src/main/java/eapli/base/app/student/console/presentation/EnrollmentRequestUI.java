package eapli.base.app.student.console.presentation;

import eapli.base.enrollmentrequest.application.EnrollmentRequestController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

public class EnrollmentRequestUI extends AbstractUI {

    private final EnrollmentRequestController ctrl;
    public EnrollmentRequestUI(){
        super();
        ctrl = new EnrollmentRequestController();
    }

    @Override
    protected boolean doShow() {
        boolean keepGoing = false;

        var widget = new SelectWidget<>("Choose a course to enroll:", this.ctrl.getCourses());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;
        var chosen = widget.selectedElement().getCourseName();

        System.out.printf("You chose: %s\n", chosen.getName());
        if (Console.readBoolean("Is this ok? (y/n)")) {
            try {
                if (this.ctrl.createEnrollmentRequest(chosen)) {
                    this.ctrl.saveEnrollmentRequest();
                    System.out.println("Enrollment request created successfully");
                }

                keepGoing = Console.readBoolean("Do you wish to open enroll in another course? (y/n)");
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
        return "Enroll in a course";
    }
}