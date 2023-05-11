package eapli.base.exam.domain.formative_exam;

import eapli.base.exam.domain.regular_exam.RegularExamQuestion;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class FormativeExamQuestion implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IDFormativeExamQuestion")
    private int id;

    @Column(name = "WEIGHT")
    private float weight;

    protected FormativeExamQuestion(float weight)
    {

    }

    public FormativeExamQuestion()
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
        if (!(other instanceof FormativeExamQuestion)) {
            return false;
        }

        final FormativeExamQuestion that = (FormativeExamQuestion) other;
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
