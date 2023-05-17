package eapli.base.app.manager.console.presentation;



import eapli.base.app.manager.console.presentation.authz.SystemUserPrinter;
import eapli.base.clientusermanagement.usermanagement.application.ListUsersController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListWidget;



public class ListTeachersStudentsManagersUI extends AbstractUI {

    private final ListUsersController ctrl ;

    public ListTeachersStudentsManagersUI()
    {

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

        new ListWidget<>(String.format("#  %-30s%-30s%-30s", "USERNAME", "F. NAME", "L. NAME"), this.ctrl.allUsersExceptPowerUser(),new SystemUserPrinter()).show();

        System.out.println("-----Teachers-----");
        new ListWidget<>(String.format("#  %-30s%-30s%-30s", "USERNAME", "F. NAME", "L. NAME"), this.ctrl.userTeachers(),new SystemUserPrinter()).show();

        System.out.println("-----Students-----");
        new ListWidget<>(String.format("#  %-30s%-30s%-30s", "USERNAME", "F. NAME", "L. NAME"), this.ctrl.userStudents(),new SystemUserPrinter()).show();

        System.out.println("-----Managers-----");
        new ListWidget<>(String.format("#  %-30s%-30s%-30s", "USERNAME", "F. NAME", "L. NAME"), this.ctrl.userManagers(),new SystemUserPrinter()).show();



        return true;
    }

}
