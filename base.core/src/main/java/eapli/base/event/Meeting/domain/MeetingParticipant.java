package eapli.base.event.Meeting.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class MeetingParticipant implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MEETINGPARTICIPANTID")
    private int id;
    @ManyToOne
    private Meeting meeting;
    @ManyToOne

    private SystemUser user;
    private MeetingParticipantStatus status;

    protected MeetingParticipant(){

    }

    public MeetingParticipant(SystemUser user, Meeting meeting) {
        this.user = user;
        this.meeting = meeting;
        this.status = MeetingParticipantStatus.PENDING;
    }

    public MeetingParticipantStatus status(){
        return this.status;
    }

    public Meeting meeting(){
        return this.meeting;
    }

    public void accept(){
        this.status = MeetingParticipantStatus.ACCEPTED;
    }
    public void deny(){
        this.status = MeetingParticipantStatus.REJECTED;
    }


    @Override
    public boolean sameAs(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingParticipant that = (MeetingParticipant) o;
        return Objects.equals(meeting, that.meeting) && Objects.equals(user, that.user);
    }


    @Override
    public int compareTo(Integer other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Integer identity() {
        return this.id;
    }

    @Override
    public boolean hasIdentity(Integer id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}
