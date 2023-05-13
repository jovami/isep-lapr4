package eapli.base.persistence.impl.inmemory;

import eapli.base.event.timetable.domain.TimeTable;
import eapli.base.event.timetable.repositories.TimeTableRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryTimeTableRepository extends InMemoryDomainRepository<TimeTable, Username> implements TimeTableRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryTimeTableRepository() {
        super();
    }

}
