package eapli.base.event.timetable.application;

import java.util.Collection;

import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.timetable.domain.TimeTable;
import eapli.base.event.timetable.repositories.TimeTableRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

@UseCaseController
public class TimeTableService {

    private final TimeTableRepository repo;

    public TimeTableService() {
        this.repo = PersistenceContext.repositories().timeTables();
    }

    public boolean checkAvailabilityByUser(SystemUser user, RecurringPattern pattern) {
        Iterable<TimeTable> tables = this.repo.findBySystemUser(user);
        for (TimeTable table : tables) {
            if (table.pattern().overLap(pattern)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkAvailability(Collection<SystemUser> names, RecurringPattern pattern) {
        for (SystemUser name : names) {
            if (!checkAvailabilityByUser(name, pattern)) {
                return false;
            }
        }
        return true;
    }

    public boolean schedule(Collection<SystemUser> users, RecurringPattern pattern) {
        for (SystemUser user : users) {
            TimeTable table = new TimeTable(user, pattern);
            if (repo.save(table) == null) {
                return false;
            }
        }

        return true;
    }
}
