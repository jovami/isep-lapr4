package eapli.base.exam.domain.formative_exam_specification;

import eapli.base.exam.domain.formative_exam_specification.valueobjects.FormativeExamTitle;
import eapli.base.exam.domain.formative_exam_specification.valueobjects.FormativeExamDescription;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class FormativeExamSpecification implements AggregateRoot<Long>, Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @AttributeOverride(name = "formativeExamTitle", column = @Column(name = "FORMATIVEEXAMTITLE"))
    @Embedded
    private FormativeExamTitle formativeExamTitle;

    @AttributeOverride(name = "formativeExamDescription", column = @Column(name = "FORMATIVEEXAMDESCRIPTION"))
    @Embedded
    private FormativeExamDescription formativeExamDescription;


    public FormativeExamSpecification(FormativeExamTitle formativeExamTitle, FormativeExamDescription formativeExamDescription)
    {
        this.formativeExamTitle = formativeExamTitle;
        this.formativeExamDescription = formativeExamDescription;
    }

    protected FormativeExamSpecification ()
    {
        //for ORM only.
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
    public boolean sameAs(final Object other) {
        if(!(other instanceof FormativeExamSpecification))
            return false;

        final FormativeExamSpecification that = (FormativeExamSpecification) other;

        if(this == that)
            return true;

        return identity().equals(that.identity());
    }

    @Override
    public int compareTo(Long other) { return AggregateRoot.super.compareTo(other); }
    @Override
    public Long identity() { return this.id; }
    @Override
    public boolean hasIdentity(Long id) { return AggregateRoot.super.hasIdentity(id); }

}
