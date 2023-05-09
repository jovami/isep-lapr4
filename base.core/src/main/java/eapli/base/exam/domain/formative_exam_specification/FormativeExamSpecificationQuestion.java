package eapli.base.exam.domain.formative_exam_specification;

import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;

@Entity
public class FormativeExamSpecificationQuestion implements AggregateRoot<Long> {

    //ORM primary key
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private String weight;

    protected FormativeExamSpecificationQuestion(String weight)
    {
        this.weight = weight;
    }

    protected FormativeExamSpecificationQuestion () {}

    @Override
    public boolean sameAs(final Object other) {
        if (!(other instanceof FormativeExamSpecificationQuestion)) {
            return false;
        }

        final FormativeExamSpecificationQuestion that = (FormativeExamSpecificationQuestion) other;
        if (this == that) {
            return true;
        }

        return id.equals(that.id) ;
    }

    @Override
    public int compareTo(Long other) {return AggregateRoot.super.compareTo(other);}

    @Override
    public Long identity() { return identity(); }

    @Override
    public boolean hasIdentity(Long id) { return AggregateRoot.super.hasIdentity(id);}
}
