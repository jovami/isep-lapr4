package eapli.base.app.common.console.presentation.clientuser;

import eapli.base.course.application.ListAvailableCoursesController;
import eapli.base.course.dto.AvailableCourseDTO;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

/**
 * ListAvailableCoursesUI
 */
public class ListAvailableCoursesUI extends AbstractListUI<AvailableCourseDTO> {
    private final ListAvailableCoursesController ctrl;

    public ListAvailableCoursesUI() {
        super();

        this.ctrl = new ListAvailableCoursesController();
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
