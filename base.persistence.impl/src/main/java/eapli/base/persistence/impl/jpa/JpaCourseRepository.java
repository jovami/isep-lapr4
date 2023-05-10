package eapli.base.persistence.impl.jpa;

import eapli.domain.Course;
import eapli.repositories.CourseRepository;

public class JpaCourseRepository extends BaseJpaRepositoryBase<Course,Long,Integer> implements CourseRepository {
    JpaCourseRepository(String persistenceUnitName) {
        super(persistenceUnitName, "courseid");
    }
    //TODO: implement

}