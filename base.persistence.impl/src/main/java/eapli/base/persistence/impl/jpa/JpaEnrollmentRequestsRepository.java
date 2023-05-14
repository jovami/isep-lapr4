package eapli.base.persistence.impl.jpa;


import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;

public class JpaEnrollmentRequestsRepository extends BaseJpaRepositoryBase<EnrollmentRequest,Long,Integer> implements EnrollmentRequestRepository {

    public JpaEnrollmentRequestsRepository(String persistenceUnitName) {
        super(persistenceUnitName, "code");
    }
}
