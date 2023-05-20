package eapli.base.enrollmentrequest.repositories;

import java.util.Optional;

import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.framework.domain.repositories.DomainRepository;

public interface EnrollmentRequestRepository extends DomainRepository<Integer, EnrollmentRequest> {

    Iterable<EnrollmentRequest> pendingEnrollmentRequests();

    Optional<EnrollmentRequest> findFirstPendingEnrollmentRequest();
}
