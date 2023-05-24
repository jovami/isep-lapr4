package eapli.base.app.teacher.console.presentation;

import eapli.base.course.application.ListAvailableCoursesTeacherController;
import eapli.base.course.dto.AvailableCourseDTO;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

/**
 * ListAvailableCoursesManagerUI
 */
public class ListAvailableCoursesTeacherUI extends AbstractListUI<AvailableCourseDTO> {
    private final ListAvailableCoursesTeacherController ctrl;

    public ListAvailableCoursesTeacherUI() {
        super();

        this.ctrl = new ListAvailableCoursesTeacherController();
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