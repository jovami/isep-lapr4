package eapli.base.course.application;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.base.course.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SetCourseTeachersController {
    private CourseRepository repo;
    private UserRepository userRepo;
    private Course course;
    public SetCourseTeachersController(){
        repo = PersistenceContext.repositories().courses();
        userRepo = PersistenceContext.repositories().users();
    }

    public void listCourses(){
        Iterable<Course> courses=repo.findAll();
        System.out.println("ID\t|Name");
        for (Course c:courses) {
            System.out.println(c.identity()+"\t|"+c.getName());
        }
    }

    public boolean chooseCourse(int id) {
        Optional<Course> opt =repo.ofIdentity(id);
        if (opt.isPresent()){
            course=opt.get();
            return true;
        }
        else
            return false;
    }
    public boolean listTeachers() {
        //QUERY
        Iterable<SystemUser> iter = userRepo.findAll();
        List<Teacher> list = new ArrayList<>();
        for (SystemUser s:iter) {
            if (s.hasAny(BaseRoles.TEACHER)){
                //list.add();

            }
        }

        return true;
    }
}
