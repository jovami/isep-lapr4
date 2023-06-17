package eapli.base.board.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import javax.persistence.*;

@Entity
@Table(name = "BOARDPARTICIPANTS")
public class BoardParticipant implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int participantId;

    @ManyToOne
    private SystemUser participant;

    @ManyToOne
    private Board board;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardParticipantPermissions permission;

    protected BoardParticipant() {
    }

    public BoardParticipant(Board board, SystemUser participant, BoardParticipantPermissions permissions) {
        this.board = board;
        this.participant = participant;
        this.permission = permissions;
    }

    public SystemUser participant() {
        return this.participant;
    }

    public Board board() {
        return this.board;
    }


    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof BoardParticipant)) {
            return false;
        }

        final BoardParticipant that = (BoardParticipant) other;
        if (this == that) {
            return true;
        }

            return board.sameAs(that.board) && participant.sameAs(that.participant);
    }

    public boolean hasWritePermissions() {
        return permission() == BoardParticipantPermissions.WRITE;
    }

    public BoardParticipantPermissions permission() {
        return this.permission;
    }

    @Override
    public int compareTo(Integer other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Integer identity() {
        return this.participantId;
    }

    @Override
    public boolean hasIdentity(Integer id) {
        return AggregateRoot.super.hasIdentity(id);
    }


    @Override
    public String toString() {
        return "BoardParticipant{" +
                "participant=" + participant +
                ", board=" + board +
                '}';
    }
}
