package eapli.base.app.teacher.console.presentation;

import eapli.base.course.application.ListExamsInCourseController;
import eapli.framework.domain.repositories.ConcurrencyException;
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
        var widget = new SelectWidget<>("Choose a course to list exams from:", this.ctrl.listCourses());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;
        var chosen = widget.selectedElement();

        try {
            new ListWidget<>("Available exams:", this.ctrl.listExams(chosen)).show();
        } catch (ConcurrencyException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public String headline() {
        return "List exams in a course";
    }
}
