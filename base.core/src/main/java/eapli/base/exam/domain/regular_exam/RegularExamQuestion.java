package eapli.base.exam.domain.regular_exam;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;

@Entity
@Table(name="REGULAREXAMQUESTION")
public class RegularExamQuestion implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDExamRegularQuestion")
    private int id;

    @Column(name = "WEIGHT")
    private float weight;

    protected RegularExamQuestion(float weight)
    {
        this.weight = weight;
    }

    //FOR ORM ONLY
    public RegularExamQuestion()
    {

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
        if (!(other instanceof RegularExamQuestion)) {
            return false;
        }

        final RegularExamQuestion that = (RegularExamQuestion) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
    }

    @Override
    public int compareTo(Integer other) {return AggregateRoot.super.compareTo(other);}

    @Override
    public Integer identity() {return this.id;}

    @Override
    public boolean hasIdentity(Integer id) {return AggregateRoot.super.hasIdentity(id);}

}
