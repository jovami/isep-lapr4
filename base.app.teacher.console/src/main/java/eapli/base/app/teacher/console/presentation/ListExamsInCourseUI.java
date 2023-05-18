package eapli.base.app.teacher.console.presentation;

import eapli.base.course.application.ListExamsInCourseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListWidget;
import eapli.framework.presentation.console.SelectWidget;

public class ListExamsInCourseUI extends AbstractUI {
    private final ListExamsInCourseController ctrl;

    public ListExamsInCourseUI() {
        super();
        ctrl = new ListExamsInCourseController();
    }

    @Override
    protected boolean doShow() {
        boolean keepGoing = false;

        var widget = new SelectWidget<>("Choose a course to list exams from:", this.ctrl.listCourses());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;
        var chosen = widget.selectedElement();

        try {
            new ListWidget<>("Available exams:", this.ctrl.listExams(chosen)).show();

            keepGoing = Console.readBoolean("Do you wish to list exams from another course? (y/n)");
        } catch (ConcurrencyException e) {
            System.out.println(e.getMessage());
            keepGoing = false;
        }

        return keepGoing;
    }

    @Override
    public String headline() {
        return "List exams in a course";
    }
}
