package eapli.base.exam.domain.regular_exam;

import eapli.base.exam.domain.regular_exam.valueobjects.ExamDate;
import eapli.base.exam.domain.regular_exam.valueobjects.ExamHeader;
import eapli.base.exam.domain.regular_exam.valueobjects.ExamTitle;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;

@Entity
public class RegularExam implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @AttributeOverride(name = "examTitle", column = @Column(name = "EXAMTITLE"))
    @Embedded
    private ExamTitle examTitle;

    @AttributeOverride(name = "examHeader", column = @Column(name = "EXAMHEADER"))
    @Embedded
    private ExamHeader examHeader;

    @Embedded
    @AttributeOverrides( value = {
            @AttributeOverride(name = "openDate", column = @Column(name = "OPENDATE")),
            @AttributeOverride(name = "closeDate", column = @Column(name = "CLOSEDATE"))
    })
    private ExamDate examDate;


    public RegularExam(ExamTitle examTitle, ExamHeader examHeader, ExamDate examDate)
    {
        this.examTitle = examTitle;
        this.examHeader = examHeader;
        this.examDate = examDate;
    }

    protected RegularExam() {
        //for ORM only
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
        if (!(other instanceof RegularExam)) {
            return false;
        }

        final RegularExam that = (RegularExam) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
    }

    @Override
    public int compareTo(Long other) {return AggregateRoot.super.compareTo(other);}

    @Override
    public Long identity() {return this.id;}

    @Override
    public boolean hasIdentity(Long id) {return AggregateRoot.super.hasIdentity(id);}

}
