package eapli.base.persistence.impl.inmemory;

import eapli.base.enrollment.domain.Enrollment;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryEnrollmentRepository extends InMemoryDomainRepository<Enrollment, Integer> implements EnrollmentRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryEnrollmentRepository() {

    }
}