package eapli.base.persistence.impl.inmemory;

import eapli.base.clientusermanagement.domain.users.MecanographicNumber;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

/**
 * InMemoryStudentRepository
 */
public class InMemoryStudentRepository extends InMemoryDomainRepository<Student, MecanographicNumber> implements StudentRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryStudentRepository() {
        super();
    }


    @Override
    public Optional<Student> findBySystemUser(SystemUser systemUser) {
        return matchOne(student -> student.user().equals(systemUser));
    }
}
