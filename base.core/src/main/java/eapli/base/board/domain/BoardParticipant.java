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
    private int id;

    @OneToOne
    private SystemUser participant;

    @OneToOne
    private Board board;


    protected BoardParticipant()
    {
    }
    public BoardParticipant(Board board, SystemUser participant)
    {
        this.board = board;
        this.participant = participant;
    }

    public SystemUser participant()
    {
        return this.participant;
    }

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
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

        return identity().equals(that.identity());
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


    @Override
    public String toString() {
        return "BoardParticipant{" +
                "participant=" + participant +
                ", board=" + board +
                '}';
    }
}
