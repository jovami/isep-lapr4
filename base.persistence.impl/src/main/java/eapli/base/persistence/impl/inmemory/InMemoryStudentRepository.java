package eapli.base.persistence.impl.inmemory;

import eapli.base.clientusermanagement.domain.MecanographicNumber;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

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
}
