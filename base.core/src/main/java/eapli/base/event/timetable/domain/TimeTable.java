package eapli.base.event.timetable.domain;

import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.infrastructure.authz.domain.model.Username;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="TIMETABLE")
public class TimeTable implements AggregateRoot<Username> {
    @EmbeddedId
    private Username username;
    private List<RecurringPattern> patterns = new ArrayList<>();

    private TimeTable(){
    }

    public TimeTable(Username userName){
        this.username = userName;
        patterns = new ArrayList<>();
    }

    public List<RecurringPattern> getPatterns() {
        return patterns;
    }

    public void addPatterns(RecurringPattern patterns) {
        this.patterns.add(patterns);
    }

    public boolean checkAvailability(RecurringPattern patternToCompare){
        for (RecurringPattern pattern: patterns) {
            if(pattern.overLap(patternToCompare)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean sameAs(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeTable timeTable = (TimeTable) o;
        return Objects.equals(username, timeTable.username);
    }


    @Override
    public int compareTo(Username other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Username identity() {
        return this.username;
    }

    @Override
    public boolean hasIdentity(Username id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}
