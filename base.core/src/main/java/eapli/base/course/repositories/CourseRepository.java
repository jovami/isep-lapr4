package eapli.base.course.repositories;

import java.util.Set;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseState;
import eapli.framework.domain.repositories.DomainRepository;

public interface CourseRepository extends DomainRepository<Integer, Course> {

    public Iterable<Course> ofState(CourseState state);
    public Iterable<Course> ofStates(Set<CourseState> states);
}
