package eapli.base.event.timetable.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

@Entity
@Table(name = "TIMETABLE")
public class TimeTable implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int timeTableId;

    @ManyToOne
    private SystemUser user;

    @ManyToOne
    private RecurringPattern pattern;

    protected TimeTable() {
        // for ORM
    }

    public TimeTable(SystemUser user, RecurringPattern pattern) {
        this.user = user;
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "TimeTable" +
                "\n timeTableId: " + timeTableId +
                "\n user: " + user.username().toString() +
                "\n pattern: " + pattern;
    }

    public RecurringPattern pattern() {
        return pattern;
    }

    @Override
    public boolean sameAs(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TimeTable timeTable = (TimeTable) o;
        return Objects.equals(user, timeTable.user) && this.pattern.equals(timeTable.pattern);
    }

    @Override
    public int compareTo(Integer other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Integer identity() {
        return timeTableId;
    }

    @Override
    public boolean hasIdentity(Integer id) {
        return AggregateRoot.super.hasIdentity(id);
    }

    public SystemUser getUser() {
        return this.user;
    }
}
