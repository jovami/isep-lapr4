package eapli.base.persistence.impl.inmemory;

import java.util.Optional;

import eapli.base.clientusermanagement.domain.users.MecanographicNumber;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 * InMemoryStudentRepository
 */
class InMemoryStudentRepository extends InMemoryDomainRepository<Student, MecanographicNumber>
        implements StudentRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryStudentRepository() {
        super();
    }

    @Override
    public Optional<Student> findByUsername(Username username) {
        return matchOne(student -> student.user().username().equals(username));
    }

    @Override
    public Optional<Student> findBySystemUser(SystemUser systemUser) {
        return matchOne(student -> student.user().sameAs(systemUser));
    }
}
