package eapli.base.exam.domain.formative_exam_specification;

import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FormativeExamSpecificationSection implements AggregateRoot<Long> {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private String description;

    protected FormativeExamSpecificationSection(String description)
    {
        this.description = description;
    }

    protected FormativeExamSpecificationSection () {}

    @Override
    public boolean sameAs(final Object other) {
        if (!(other instanceof FormativeExamSpecificationSection)) {
            return false;
        }

        final FormativeExamSpecificationSection that = (FormativeExamSpecificationSection) other;
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
