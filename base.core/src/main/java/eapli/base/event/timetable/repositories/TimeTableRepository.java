package eapli.base.event.timetable.repositories;

import eapli.base.event.timetable.domain.TimeTable;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

public interface TimeTableRepository extends DomainRepository<Integer, TimeTable> {

    // Optional<TimeTable> findBySystemUser(SystemUser user);
    Iterable<TimeTable> findBySystemUser(SystemUser user);
}
