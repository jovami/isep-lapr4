package eapli.base.course.application;

import java.util.HashSet;
import java.util.Set;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.StaffMember;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;

@UseCaseController
public class SetCourseTeachersController {
    private final CourseRepository repo;
    private final TeacherRepository teacherRepo;
    private final StaffRepository staffRepo;
    private final Set<Teacher> staff = new HashSet<>();
    private Course course;

    public SetCourseTeachersController() {
        repo = PersistenceContext.repositories().courses();
        teacherRepo = PersistenceContext.repositories().teachers();
        staffRepo = PersistenceContext.repositories().staffs();

    }

    public void chooseCourse(Course course) {
        this.course = course;
    }

    public Iterable<Course> courses() {
        return repo.findAll();
    }

    public boolean chooseHeadTeacher(Teacher teacher) {
        course.setHeadTeacher(teacher);
        return repo.save(course) != null;
    }

    public Iterable<Teacher> teachers() {
        return teacherRepo.findAll();
    }

    public boolean addStaffMember(Teacher teacher) {
        if (staff.add(teacher) && course.headTeacher() != teacher) {
            StaffMember newMember = new StaffMember(course, teacher);
            return staffRepo.save(newMember) != null;
        }
        return false;
    }

    public Iterable<Teacher> staff() {
        return staffRepo.findByCourse(course);

    }
}
