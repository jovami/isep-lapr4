package eapli.base.app.manager.console.presentation;

import eapli.base.course.application.SetCourseTeachersController;
import eapli.framework.presentation.console.AbstractUI;

import eapli.framework.io.util.Console;

public class SetCourseTeacherUI extends AbstractUI {

    private SetCourseTeachersController ctrl = new SetCourseTeachersController();

    @Override
    protected boolean doShow() {
        ctrl.listCourses();
        int id=Console.readInteger("Choose id course: ");
        if(ctrl.chooseCourse(id)){

        }else {
            System.out.println("There is no course with the given id");
        }
        return true;
    }

    @Override
    public String headline() {
        return "Set Course Teachers";
    }
}
