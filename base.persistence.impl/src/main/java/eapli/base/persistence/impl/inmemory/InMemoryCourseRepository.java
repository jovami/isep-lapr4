package eapli.base.persistence.impl.inmemory;

import eapli.base.course.domain.Course;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import eapli.base.course.repositories.CourseRepository;

public class InMemoryCourseRepository extends InMemoryDomainRepository<Course, Integer> implements CourseRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryCourseRepository() {

    }
}
