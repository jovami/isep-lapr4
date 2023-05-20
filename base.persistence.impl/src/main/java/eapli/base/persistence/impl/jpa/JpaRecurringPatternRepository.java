package eapli.base.persistence.impl.jpa;

import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;

class JpaRecurringPatternRepository extends BaseJpaRepositoryBase<RecurringPattern, Long, Integer>
        implements RecurringPatternRepository {

    JpaRecurringPatternRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, identityFieldName);
    }

    JpaRecurringPatternRepository(String identityFieldName) {
        super(identityFieldName);
    }
}
