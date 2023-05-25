package eapli.base.course.application;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.StaffMember;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import org.eclipse.collections.impl.factory.HashingStrategySets;

import java.util.ArrayList;
import java.util.Set;

import static org.eclipse.collections.impl.block.factory.HashingStrategies.fromFunction;

@UseCaseController
public class SetCourseTeachersController {
    private final CourseRepository repo;
    private final StaffRepository staffRepo;
    private final Set<Teacher> staff;

    // FIXME: use DTO
    private ArrayList<Teacher> teachers = new ArrayList<>();
    private Course course;

    public SetCourseTeachersController() {
        repo = PersistenceContext.repositories().courses();
        staffRepo = PersistenceContext.repositories().staffs();
        this.staff = HashingStrategySets.mutable.with(fromFunction(Teacher::identity));
    }

    public ArrayList<Teacher> teachersNotInStaff(){
        //collections
        teachers = (ArrayList<Teacher>) PersistenceContext.repositories().teachers().findAll();
        for (Teacher tch: staff()) {
            teachers.remove(tch);
        }
        if (course.headTeacher().isPresent()){
            teachers.remove(course.headTeacher().get());
        }
        return teachers;
    }
    public Iterable<Teacher> staff() {
        return staffRepo.findByCourse(course);
    }

    public void chooseCourse(Course course) {
        this.course = course;
        teachersNotInStaff();
    }

    public Iterable<Course> courses() {
        return repo.findAll();
    }

    public boolean chooseHeadTeacher(Teacher teacher) {
        course.setHeadTeacher(teacher);
        this.teachers.remove(teacher);
        return repo.save(course) != null;
    }

    public Iterable<Teacher> teachers() {
        if (teachers.isEmpty()){
            return null;
        }
        return teachers;
    }

    public boolean addStaffMember(Teacher teacher) {

        boolean notHead = course.headTeacher()
                .map(t -> !teacher.sameAs(t))
                .orElse(false);

        if (staff.add(teacher) && notHead) {
            this.teachers.remove(teacher);
            return staffRepo.save(new StaffMember(course, teacher)) != null;
        }
        return false;
    }

    public String headTeacher() {
        if(course.headTeacher().isPresent()){
            return course.headTeacher().get().acronym().toString();
        }
        return null;

    }

}
