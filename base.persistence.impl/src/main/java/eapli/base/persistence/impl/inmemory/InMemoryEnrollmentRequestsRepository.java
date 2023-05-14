package eapli.base.persistence.impl.inmemory;

import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryEnrollmentRequestsRepository extends InMemoryDomainRepository<EnrollmentRequest, Integer> implements EnrollmentRequestRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryEnrollmentRequestsRepository() {

    }

}
