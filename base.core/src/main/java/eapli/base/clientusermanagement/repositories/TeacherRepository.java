package eapli.base.clientusermanagement.repositories;

import eapli.base.clientusermanagement.domain.users.Acronym;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 * TeacherRepository
 */
public interface TeacherRepository extends DomainRepository<Acronym, Teacher> {

    Teacher findBySystemUser(Username username);
}
