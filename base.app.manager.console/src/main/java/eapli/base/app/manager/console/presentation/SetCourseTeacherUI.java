package eapli.base.app.manager.console.presentation;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.course.application.SetCourseTeachersController;
import eapli.base.course.domain.Course;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

public class SetCourseTeacherUI extends AbstractUI {

    private SetCourseTeachersController ctrl = new SetCourseTeachersController();

    @Override
    protected boolean doShow() {

        SelectWidget<Course> selecCourse = new SelectWidget<>("Choose Course", ctrl.courses());
        selecCourse.show();
        ctrl.chooseCourse(selecCourse.selectedElement());

        boolean headTeacherCoosed = false;
        while (!headTeacherCoosed) {
            SelectWidget<Teacher> selec = new SelectWidget<>("Choose Head Teacher", ctrl.teachers());
            selec.show();
            if (ctrl.chooseHeadTeacher(selec.selectedElement())) {
                headTeacherCoosed = true;
            } else {
                System.out.println("There was an error choosing the head teacher");
            }
        }

        SelectWidget<Teacher> selecMember = new SelectWidget<>("Choose new staff member", ctrl.teachers());
        do {
            selecMember.show();
            if (selecMember.selectedOption() != 0) {
                if (ctrl.addStaffMember(selecMember.selectedElement())) {
                    System.out.println("Staff member added with succes");
                } else {
                    System.out.println("There was an error with the new staff member");
                }
            }

        } while (selecMember.selectedOption() != 0);

        System.out.println("\n\tSTAFF MEMBERS:");
        for (Teacher member : ctrl.staff()) {
            System.out.println(" - " + member.acronym());
        }
        ;
        return true;
    }

    @Override
    public String headline() {
        return "Set Course Teachers";
    }
}
