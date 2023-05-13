package eapli.base.event.timetable.application;

import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.timetable.domain.TimeTable;
import eapli.base.event.timetable.repositories.TimeTableRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;

import java.util.Collection;
import java.util.Optional;

public class CheckAvailabilityService {

    private final TimeTableRepository repo;

    public CheckAvailabilityService(TimeTableRepository repo) {
        this.repo = repo;
    }

    public boolean checkAvailabilityByUser(Username name, RecurringPattern pattern) {
        Optional<TimeTable> table = this.repo.ofIdentity(name);
        return table.map(timeTable -> timeTable.checkAvailability(pattern)).orElse(false);
    }
    public boolean checkAvailability(Collection<Username> names, RecurringPattern pattern) {
        for (Username name: names) {
            if (!checkAvailabilityByUser(name,pattern)){
                 return false;
            }
        }
        return true;
    }
}
