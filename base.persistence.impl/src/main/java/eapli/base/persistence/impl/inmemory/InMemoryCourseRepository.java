package eapli.base.persistence.impl.inmemory;

import java.util.Set;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseName;
import eapli.base.course.domain.CourseState;
import eapli.base.course.repositories.CourseRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

class InMemoryCourseRepository extends InMemoryDomainRepository<Course, Integer> implements CourseRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryCourseRepository() {

    }

    @Override
    public Iterable<Course> ofState(CourseState state) {
        return match(course -> course.state() == state);
    }

    @Override
    public Iterable<Course> ofStates(Set<CourseState> states) {
        return match(course -> states.contains(course.state()));
    }

    @Override
    public Course findCourseByName(CourseName courseName) {
        return matchOne((course) -> course.courseName().equals(courseName)).get();
    }
}
