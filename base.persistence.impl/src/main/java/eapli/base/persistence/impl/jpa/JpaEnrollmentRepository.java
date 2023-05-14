package eapli.base.persistence.impl.jpa;

import eapli.base.enrollment.domain.Enrollment;
import eapli.base.enrollment.repositories.EnrollmentRepository;

public class JpaEnrollmentRepository extends BaseJpaRepositoryBase<Enrollment,Long,Integer> implements EnrollmentRepository {
    public JpaEnrollmentRepository(String persistenceUnitName) {
        super(persistenceUnitName, "code");
    }
}
