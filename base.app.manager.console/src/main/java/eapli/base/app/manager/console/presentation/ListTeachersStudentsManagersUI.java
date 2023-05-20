package eapli.base.app.manager.console.presentation;

import eapli.base.clientusermanagement.usermanagement.application.ListUsersController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListWidget;

public class ListTeachersStudentsManagersUI extends AbstractUI {

    private final ListUsersController ctrl;

    public ListTeachersStudentsManagersUI() {

        this.ctrl = new ListUsersController();
    }

    @Override
    public String headline() {
        return "List user of the system(Teachers, Students and Managers)";
    }

    @Override
    protected boolean doShow() {

        new ListWidget<>("Teachers", this.ctrl.listTeachers()).show();
        new ListWidget<>("Students", this.ctrl.listStudents()).show();
        new ListWidget<>("Managers", this.ctrl.listManagers()).show();

        return false;
    }

}
