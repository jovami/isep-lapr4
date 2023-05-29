package eapli.base.app.teacher.console.presentation;

import eapli.base.examresult.application.ListExamsGradesInCourseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListWidget;
import eapli.framework.presentation.console.SelectWidget;

public class ListExamsGradesInCourseUI extends AbstractUI {

    private final ListExamsGradesInCourseController ctrl;

    public ListExamsGradesInCourseUI(){
        super();
        this.ctrl = new ListExamsGradesInCourseController();
    }

    @Override
    protected boolean doShow() {
        var widget = new SelectWidget<>("Choose a course to list exams grades from:", this.ctrl.listCourses());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;
        var chosen = widget.selectedElement();

        try {
            new ListWidget<>("Exams results:", this.ctrl.listExamsGradesAndStudents(chosen)).show();
        } catch (ConcurrencyException e) {
            System.out.println(e.getMessage());
        }

        return false;

    }

    @Override
    public String headline() {
        return "List exams results";
    }


}
