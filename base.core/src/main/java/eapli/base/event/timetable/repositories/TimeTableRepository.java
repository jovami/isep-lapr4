package eapli.base.event.timetable.repositories;

import eapli.base.event.timetable.domain.TimeTable;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;

public interface TimeTableRepository  extends DomainRepository<Username, TimeTable> {
}
