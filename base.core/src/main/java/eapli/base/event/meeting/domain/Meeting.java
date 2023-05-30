package eapli.base.event.meeting.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

@Entity
@Table(name = "MEETING")
public class Meeting implements AggregateRoot<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEETINGID")
    private int meetingId;

    @OneToOne
    private SystemUser meetingAdmin;

    private Description description;

    @OneToOne
    private RecurringPattern pattern;

    protected Meeting() {
    }

    public Meeting(SystemUser user, String description, RecurringPattern pattern) {
        this.meetingAdmin = user;
        this.description = new Description(description);
        this.pattern = pattern;
    }

    public SystemUser admin() {
        return meetingAdmin;
    }

    protected void chooseMeetingAdmin(SystemUser meetingAdmin) {
        this.meetingAdmin = meetingAdmin;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
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
        Meeting meeting = (Meeting) o;
        return Objects.equals(meetingId, meeting.meetingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, meetingAdmin, pattern);
    }

    @Override
    public int compareTo(Integer other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Integer identity() {
        return this.meetingId;
    }

    @Override
    public boolean hasIdentity(Integer id) {
        return this.meetingId == id;
    }

}
