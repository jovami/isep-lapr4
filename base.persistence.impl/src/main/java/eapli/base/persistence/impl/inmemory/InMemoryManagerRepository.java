package eapli.base.persistence.impl.inmemory;

import eapli.base.clientusermanagement.domain.users.Manager;
import eapli.base.clientusermanagement.domain.users.ManagerId;
import eapli.base.clientusermanagement.repositories.ManagerRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 * InMemoryManagerRepository
 */
public class InMemoryManagerRepository extends InMemoryDomainRepository<Manager, ManagerId> implements ManagerRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryManagerRepository() {
        super();
    }
}
