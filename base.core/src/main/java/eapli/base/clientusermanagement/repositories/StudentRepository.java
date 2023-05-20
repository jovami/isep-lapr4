package eapli.base.clientusermanagement.repositories;

import java.util.Optional;

import eapli.base.clientusermanagement.domain.users.MecanographicNumber;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 * StudentRepository
 */
public interface StudentRepository extends DomainRepository<MecanographicNumber, Student> {
    Optional<Student> findByUsername(Username username);

    Optional<Student> findBySystemUser(SystemUser systemUser);
}
