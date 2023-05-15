package eapli.base.persistence.impl.jpa;

import eapli.base.clientusermanagement.domain.users.MecanographicNumber;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.Optional;

/**
 * JpaStudentRepository
 */
public class JpaStudentRepository extends BaseJpaRepositoryBase<Student, Long, MecanographicNumber> implements StudentRepository {

    JpaStudentRepository(String identityFieldName) {
        super(identityFieldName, "mecanographicNumber");
    }

    @Override
    public Optional<Student> findBySystemUser(SystemUser systemUser) {
        return matchOne("e.systemUser=:systemUser", "systemUser", systemUser);
    }
}
