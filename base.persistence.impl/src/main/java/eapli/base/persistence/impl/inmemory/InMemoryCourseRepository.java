package eapli.base.persistence.impl.inmemory;

import java.util.Optional;
import java.util.Set;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseName;
import eapli.base.course.domain.CourseState;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import eapli.base.course.repositories.CourseRepository;

public class InMemoryCourseRepository extends InMemoryDomainRepository<Course, Integer> implements CourseRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryCourseRepository() {

    }

    @Override
    public Iterable<Course> ofState(CourseState state) {
        return match((course) -> course.state() == state);
    }

    @Override
    public Iterable<Course> ofStates(Set<CourseState> states) {
        return match((course) -> states.contains(course.state()));
    }

}
