package eapli.base.persistence.impl.jpa;

import java.util.Optional;

import eapli.base.clientusermanagement.domain.users.MecanographicNumber;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 * JpaStudentRepository
 */
class JpaStudentRepository extends BaseJpaRepositoryBase<Student, Long, MecanographicNumber>
        implements StudentRepository {

    JpaStudentRepository(String identityFieldName) {
        super(identityFieldName, "mecanographicNumber");
    }

    @Override
    public Optional<Student> findByUsername(Username username) {
        return matchOne("e.systemUser.username=:username", "username", username);
    }

    @Override
    public Optional<Student> findBySystemUser(SystemUser systemUser) {
        return matchOne("e.systemUser=:systemUser", "systemUser", systemUser);
    }
}
