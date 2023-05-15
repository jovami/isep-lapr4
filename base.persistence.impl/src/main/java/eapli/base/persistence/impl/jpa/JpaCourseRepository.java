package eapli.base.persistence.impl.jpa;

import java.util.Set;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseState;
import eapli.base.course.repositories.CourseRepository;

public class JpaCourseRepository extends BaseJpaRepositoryBase<Course, Long, Integer> implements CourseRepository {

    JpaCourseRepository(String persistenceUnitName) {
        super(persistenceUnitName, "code");
    }

    @Override
    public Iterable<Course> ofStates(Set<CourseState> states) {
        return match("e.state IN :states", "states", states);
    }

    @Override
    public Iterable<Course> ofState(CourseState state) {
        return match("e.state = :state", "state", state);
    }
}
