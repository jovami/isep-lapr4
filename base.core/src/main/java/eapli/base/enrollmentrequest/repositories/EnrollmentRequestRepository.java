package eapli.base.enrollmentrequest.repositories;

import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

public interface EnrollmentRequestRepository extends DomainRepository<Integer, EnrollmentRequest> {
    Iterable<EnrollmentRequest> pendingEnrollmentRequests();

    Optional<EnrollmentRequest> findFirstPendingEnrollmentRequest();
}
