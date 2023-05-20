package eapli.base.course.repositories;

import java.util.Optional;
import java.util.Set;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseName;
import eapli.base.course.domain.CourseState;
import eapli.framework.domain.repositories.DomainRepository;

public interface CourseRepository extends DomainRepository<Integer, Course> {

    Iterable<Course> ofState(CourseState state);
    Iterable<Course> ofStates(Set<CourseState> states);

    default Iterable<Course> enrollable() {
        return ofState(CourseState.ENROLL);
    }
    Course findCourseByName(CourseName courseName);


}
