package eapli.base.persistence.impl.inmemory;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.event.timetable.domain.TimeTable;
import eapli.base.event.timetable.repositories.TimeTableRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryTimeTableRepository extends InMemoryDomainRepository<TimeTable, Integer> implements TimeTableRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryTimeTableRepository() {
        super();
    }
/*
    @Override
    public Optional<TimeTable> findBySystemUser(SystemUser user) {
        return matchOne((timeTable -> user.equals(timeTable.getUser())));
    }*/
    @Override
    public Iterable<TimeTable> findBySystemUser(SystemUser user) {
        return match((timeTable -> user.equals(timeTable.getUser())));
    }
}
