package eapli.base.persistence.impl.jpa;

import java.util.Optional;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.enrollmentrequest.domain.EnrollmentRequestState;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.framework.domain.repositories.TransactionalContext;

class JpaEnrollmentRequestsRepository extends BaseJpaRepositoryBase<EnrollmentRequest, Long, Integer>
        implements EnrollmentRequestRepository {

    public JpaEnrollmentRequestsRepository(final TransactionalContext autoTx) {
        super(autoTx.toString(), "code");
    }

    public JpaEnrollmentRequestsRepository(final String persistenceUnitName) {
        super(persistenceUnitName, "code");
    }

    @Override
    public Iterable<Course> coursesOfEnrollmentRequestsByStudent(Student s) {
        final var query = entityManager().createQuery(
                "SELECT e.course FROM EnrollmentRequest e WHERE e.student = :student",
                Course.class);
        query.setParameter("student", s);
        return query.getResultList();
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
