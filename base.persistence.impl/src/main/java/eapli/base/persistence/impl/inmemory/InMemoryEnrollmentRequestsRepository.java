package eapli.base.persistence.impl.inmemory;

import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

public class InMemoryEnrollmentRequestsRepository extends InMemoryDomainRepository<EnrollmentRequest, Integer> implements EnrollmentRequestRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryEnrollmentRequestsRepository() {

    }

    @Override
    public Iterable<EnrollmentRequest> pendingEnrollmentRequests() {
        return match(EnrollmentRequest::isPending);
    }

    @Override
    public Optional<EnrollmentRequest> findFirstPendingEnrollmentRequest() {
        return matchOne(EnrollmentRequest::isPending);
    }
}
