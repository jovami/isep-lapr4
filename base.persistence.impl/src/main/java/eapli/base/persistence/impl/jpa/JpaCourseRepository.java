package eapli.base.persistence.impl.jpa;

import java.util.Set;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseID;
import eapli.base.course.domain.CourseState;
import eapli.base.course.repositories.CourseRepository;

class JpaCourseRepository extends BaseJpaRepositoryBase<Course, Long, CourseID> implements CourseRepository {

    JpaCourseRepository(String persistenceUnitName) {
        super(persistenceUnitName, "id");
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
