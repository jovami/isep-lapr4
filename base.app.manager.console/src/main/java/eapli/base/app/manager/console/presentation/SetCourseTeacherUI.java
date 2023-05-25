package eapli.base.app.manager.console.presentation;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.course.application.SetCourseTeachersController;
import eapli.base.course.domain.Course;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.util.List;

public class SetCourseTeacherUI extends AbstractUI {

    private SetCourseTeachersController ctrl = new SetCourseTeachersController();

    @Override
    protected boolean doShow() {

        SelectWidget<Course> selecCourse = new SelectWidget<>("Choose Course", ctrl.courses());
        selecCourse.show();
        ctrl.chooseCourse(selecCourse.selectedElement());

        List<Teacher> teachers = (List<Teacher>) ctrl.teachers();
        if(teachers==null){
            System.out.println("There are no teachers available at this moment");
            return false;
        }

        boolean headTeacherCoosed = false;
        while (!headTeacherCoosed) {
            SelectWidget<Teacher> selec = new SelectWidget<>("Choose Head Teacher",teachers);
            selec.show();
            if (ctrl.chooseHeadTeacher(selec.selectedElement())) {
                headTeacherCoosed = true;
            } else {
                System.out.println("There was an error choosing the head teacher");
            }
        }

        do {
        } while (selectStaffMember());

        System.out.println("\n\tSTAFF MEMBERS:");
        System.out.println(" - "+ctrl.headTeacher()+" (Head teacher)");
        for (Teacher member : ctrl.staff()) {
            System.out.println(" - " + member.acronym());
        }
        return true;
    }

    public boolean selectStaffMember(){
        if(ctrl.teachers()==null){
            System.out.println("\n\tThere are no more teachers available");
            return false;
        }
        SelectWidget<Teacher>selecMember = new SelectWidget<>("Choose new staff member", ctrl.teachersNotInStaff());
        selecMember.show();
        if (selecMember.selectedOption() != 0) {
            if (ctrl.addStaffMember(selecMember.selectedElement())) {
                System.out.println("\n\tStaff member added with succes\n");
            } else {
                System.out.println("\n\tThere was an error with the new staff member\n");
            }
            return true;
        }
        return false;
    }
    @Override
    public String headline() {
        return "Set Course Teachers";
    }
}
