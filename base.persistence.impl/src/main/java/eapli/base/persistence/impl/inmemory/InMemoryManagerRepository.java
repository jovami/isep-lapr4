package eapli.base.persistence.impl.inmemory;

import eapli.base.clientusermanagement.domain.users.Manager;
import eapli.base.clientusermanagement.domain.users.ManagerId;
import eapli.base.clientusermanagement.repositories.ManagerRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 * InMemoryManagerRepository
 */
public class InMemoryManagerRepository extends InMemoryDomainRepository<Manager, Integer> implements ManagerRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryManagerRepository() {
        super();
    }

    @Override
    public Manager findBySystemUser(Username username) {
        return matchOne(manager -> manager.user().username().equals(username)).get();
    }
}
