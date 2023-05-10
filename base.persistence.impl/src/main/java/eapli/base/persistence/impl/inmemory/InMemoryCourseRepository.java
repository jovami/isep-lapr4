package eapli.base.persistence.impl.inmemory;

import eapli.domain.Course;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import eapli.repositories.CourseRepository;

public class InMemoryCourseRepository extends InMemoryDomainRepository<Course, Integer> implements CourseRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryCourseRepository() {

    }
}
