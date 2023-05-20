package eapli.base.clientusermanagement.repositories;

import java.util.Optional;

import eapli.base.clientusermanagement.domain.users.Acronym;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 * TeacherRepository
 */
public interface TeacherRepository extends DomainRepository<Acronym, Teacher> {

    // TODO change return type to Optional<Teacher>
    @Deprecated
    Teacher findBySystemUser(Username username);

    Optional<Teacher> findBySystemUser(SystemUser user);

}
