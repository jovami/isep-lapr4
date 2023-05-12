package eapli.base.persistence.impl.jpa;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseState;
import eapli.base.course.repositories.CourseRepository;

public class JpaCourseRepository extends BaseJpaRepositoryBase<Course,Long,Integer> implements CourseRepository {
    JpaCourseRepository(String persistenceUnitName) {
        super(persistenceUnitName, "code");
    }

    // FIXME: find how to use JPQL query for this method
    @Override
    public Iterable<Course> ofStates(Set<CourseState> states) {
        return StreamSupport.stream(findAll().spliterator(), false)
                            .filter((course) -> states.contains(course.state()))
                            .collect(Collectors.toList());
    }

    // FIXME: find how to use JPQL query for this method
    @Override
    public Iterable<Course> ofState(CourseState state) {
        return StreamSupport.stream(findAll().spliterator(), false)
                            .filter((course) -> course.state() == state)
                            .collect(Collectors.toList());
    }
}
