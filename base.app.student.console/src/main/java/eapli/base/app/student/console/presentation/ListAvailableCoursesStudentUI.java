package eapli.base.app.student.console.presentation;


import eapli.base.course.application.ListAvailableCoursesStudentController;
import eapli.base.course.dto.AvailableCourseDTO;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

/**
 * ListAvailableCoursesManagerUI
 */
public class ListAvailableCoursesStudentUI extends AbstractListUI<AvailableCourseDTO> {
    private final ListAvailableCoursesStudentController ctrl;

    public ListAvailableCoursesStudentUI() {
        super();

        this.ctrl = new ListAvailableCoursesStudentController();
    }

    @Override
    public String headline() {
        return "List available courses";
    }

    @Override
    protected String elementName() {
        return "Course";
    }

    @Override
    protected Visitor<AvailableCourseDTO> elementPrinter() {
        return System.out::println;
    }

    @Override
    protected Iterable<AvailableCourseDTO> elements() {
        return this.ctrl.availableCourses();
    }

    @Override
    protected String emptyMessage() {
        return "No courses.";
    }

    @Override
    protected String listHeader() {
        return "All currently registered courses:";
    }
}
