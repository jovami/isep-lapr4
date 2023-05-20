package eapli.base.clientusermanagement.repositories;

import eapli.base.clientusermanagement.domain.users.Manager;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 * ManagerRepository
 */
public interface ManagerRepository extends DomainRepository<Integer, Manager> {
    Manager findBySystemUser(Username username);
}
