package eapli.base.app.common.console.presentation.clientuser;

import eapli.base.course.application.ListAvailableCoursesController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListUI;
import eapli.framework.presentation.console.ListWidget;

/**
 * ListAvailableCoursesUI
 */
// TODO: use ListUI?
public class ListAvailableCoursesUI extends AbstractUI {
    private final ListAvailableCoursesController ctrl;

    public ListAvailableCoursesUI() {
        super();

        this.ctrl = new ListAvailableCoursesController();
    }

    @Override
    protected boolean doShow() {
        new ListWidget<>("All currently registered courses:", this.ctrl.availableCourses())
                .show();
        return false;
    }

    @Override
    public String headline() {
        return "List available courses";
    }
}
