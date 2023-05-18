package eapli.base.event.timetable.domain;

import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="TIMETABLE")
public class TimeTable implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int timeTableId;

    @ManyToOne
    private SystemUser user;

    @ManyToOne
    private RecurringPattern pattern;

    private TimeTable(){
    }


    public TimeTable(SystemUser user,RecurringPattern pattern){
        this.user = user;
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "timeTableId=" + timeTableId +
                "\n user=" + user.username().toString() +
                "\n pattern =" + pattern +
                '}';
    }
    public RecurringPattern pattern() {
        return pattern;
    }

    @Override
    public boolean sameAs(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
