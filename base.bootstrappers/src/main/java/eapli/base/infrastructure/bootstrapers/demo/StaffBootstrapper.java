package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.clientusermanagement.domain.users.Acronym;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.StaffMember;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.actions.Action;

public class StaffBootstrapper implements Action {
    @Override
    public boolean execute() {
        CourseRepository courseRepository = PersistenceContext.repositories().courses();
        var course = courseRepository.findAll();

        TeacherRepository teacherRepository = PersistenceContext.repositories().teachers();
        Teacher teacherJFA = teacherRepository.ofIdentity(Acronym.valueOf("JFA"))
                .orElseThrow(IllegalStateException::new);
        Teacher teacherMAM = teacherRepository.ofIdentity(Acronym.valueOf("MAM"))
                .orElseThrow(IllegalStateException::new);

        for (Course c : course) {
            switch (c.identity().title()) {
                case "Fisica":
                    saveStaff(c, teacherJFA);
                    saveStaff(c, teacherMAM);
                    break;
                case "Quimica":
                    saveStaff(c, teacherJFA);
                    break;
                case "Matematica":
                    saveStaff(c, teacherMAM);
                    break;
            }
        }

        return true;
    }

    private void saveStaff(Course c, Teacher t) {
        StaffRepository repo = PersistenceContext.repositories().staffs();
        try {
            StaffMember sm = new StaffMember(c, t);
            repo.save(sm);
        } catch (IllegalArgumentException e) {
            System.out.printf("StaffMember %s was not bootstrapped in course %s\n", t, c);
            throw new RuntimeException(e);
        }
    }
}
