package eapli.base.persistence.impl.inmemory;

import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

class InMemoryRecurringPatternRepository extends InMemoryDomainRepository<RecurringPattern, Integer>
        implements RecurringPatternRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryRecurringPatternRepository() {
        super();
    }
}
