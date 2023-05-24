package eapli.base.app.manager.console.presentation;

import eapli.base.course.application.ListAvailableCoursesManagerController;
import eapli.base.course.dto.AvailableCourseDTO;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

/**
 * ListAvailableCoursesManagerUI
 */
public class ListAvailableCoursesManagerUI extends AbstractListUI<AvailableCourseDTO> {
    private final ListAvailableCoursesManagerController ctrl;

    public ListAvailableCoursesManagerUI() {
        super();

        this.ctrl = new ListAvailableCoursesManagerController();
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

