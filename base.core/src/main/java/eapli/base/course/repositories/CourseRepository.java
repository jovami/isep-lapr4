package eapli.base.course.repositories;

import java.util.Set;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseID;
import eapli.base.course.domain.CourseState;
import eapli.framework.domain.repositories.DomainRepository;

public interface CourseRepository extends DomainRepository<CourseID, Course> {

    Iterable<Course> ofState(CourseState state);

    Iterable<Course> ofStates(Set<CourseState> states);

    default Iterable<Course> openable() {
        return ofState(CourseState.CLOSE);
    }

    default Iterable<Course> closable() {
        return ofStates(Set.of(CourseState.CLOSE, CourseState.OPEN, CourseState.ENROLL, CourseState.INPROGRESS));
    }

    default Iterable<Course> openableToEnrollments() {
        return ofState(CourseState.OPEN);
    }

    default Iterable<Course> enrollable() {
        return ofState(CourseState.ENROLL);
    }
}
