package eapli.base.persistence.impl.jpa;

import eapli.base.course.domain.Course;
import eapli.base.course.repositories.CourseRepository;

public class JpaCourseRepository extends BaseJpaRepositoryBase<Course,Long,Integer> implements CourseRepository {
    JpaCourseRepository(String persistenceUnitName) {
        super(persistenceUnitName, "courseid");
    }
    //TODO: implement

}