package eapli.base.clientusermanagement.repositories;

import eapli.base.clientusermanagement.domain.users.Manager;
import eapli.base.clientusermanagement.domain.users.ManagerId;
import eapli.framework.domain.repositories.DomainRepository;

/**
 * ManagerRepository
 */
public interface ManagerRepository extends DomainRepository<ManagerId, Manager> {
}
