package eapli.base.app.student.console.presentation;

import eapli.base.examresult.application.ListStudentGradesController;
import eapli.base.examresult.dto.ExamGradeAndCourseDTO;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

public class ListGradesUI extends AbstractListUI<ExamGradeAndCourseDTO> {

    private final ListStudentGradesController ctrl;

    public ListGradesUI() {
        super();

        this.ctrl = new ListStudentGradesController();
    }

    @Override
    protected Iterable<ExamGradeAndCourseDTO> elements() {
        return this.ctrl.listStudentGrades();
    }

    @Override
    protected Visitor<ExamGradeAndCourseDTO> elementPrinter() {
        return System.out::println;
    }

    @Override
    protected String elementName() {
        return "";
    }

    @Override
    protected String listHeader() {
        return "Exam Grades:";
    }

    @Override
    protected String emptyMessage() {
        return "There are no grades available to display!";
    }

    @Override
    public String headline() {
        return "List Exam Grades";
    }
}
