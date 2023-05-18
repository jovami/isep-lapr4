package eapli.base.persistence.impl.jpa;


import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.enrollmentrequest.domain.EnrollmentRequestState;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.framework.domain.repositories.TransactionalContext;

import java.util.Optional;

public class JpaEnrollmentRequestsRepository extends BaseJpaRepositoryBase<EnrollmentRequest,Long,Integer>
        implements EnrollmentRequestRepository {

    public JpaEnrollmentRequestsRepository(final TransactionalContext autoTx) {
        super(autoTx.toString(), "code");
    }
    public JpaEnrollmentRequestsRepository(final String persistenceUnitName) {
        super(persistenceUnitName, "code");
    }

    @Override
    public Iterable<EnrollmentRequest> pendingEnrollmentRequests() {
        return match("e.state = :state", "state", EnrollmentRequestState.PENDING);
    }

    @Override
    public Optional<EnrollmentRequest> findFirstPendingEnrollmentRequest() {
        return matchOne("e.state = :state", "state", EnrollmentRequestState.PENDING);
    }
}
