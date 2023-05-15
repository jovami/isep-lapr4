package eapli.base.persistence.impl.inmemory;

import eapli.base.clientusermanagement.domain.users.Acronym;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 * InMemoryTeacherRepository
 */
public class InMemoryTeacherRepository extends InMemoryDomainRepository<Teacher, Acronym> implements TeacherRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryTeacherRepository() {
        super();
    }

    @Override
    public Teacher findBySystemUser(Username username) {
        return matchOne(teacher -> teacher.user().username().equals(username)).get();
    }
}
