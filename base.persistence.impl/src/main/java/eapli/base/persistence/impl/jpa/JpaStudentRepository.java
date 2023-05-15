package eapli.base.persistence.impl.jpa;

import eapli.base.clientusermanagement.domain.users.MecanographicNumber;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;

/**
 * JpaStudentRepository
 */
public class JpaStudentRepository extends BaseJpaRepositoryBase<Student, Long, MecanographicNumber> implements StudentRepository {

    JpaStudentRepository(String identityFieldName) {
        super(identityFieldName, "mecanographicNumber");
    }
}
