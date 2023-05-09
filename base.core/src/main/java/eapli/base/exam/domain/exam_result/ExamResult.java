package eapli.base.exam.domain.exam_result;

import eapli.base.exam.domain.exam_result.valueobjects.ExamFeedback;
import eapli.base.exam.domain.exam_result.valueobjects.ExamGrade;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;

@Entity
public class ExamResult implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @AttributeOverride(name = "examGrade", column = @Column(name = "EXAMGRADE"))
    @Embedded
    private ExamGrade examGrade;

    @AttributeOverride(name = "examFeedback", column = @Column(name = "EXAMFEEDBACK"))
    @Embedded
    private ExamFeedback examFeedback;

    // TODO : verificar se este "period" e uma string
    @Embedded
    private String period;

    public ExamResult(ExamGrade examGrade, ExamFeedback examFeedback, String period)
    {
        this.examGrade = examGrade;
        this.examFeedback = examFeedback;
        this.period = period;
    }

    protected ExamResult () {
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
        if (!(other instanceof ExamResult)) {
            return false;
        }

        final ExamResult that = (ExamResult) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
    }

    @Override
    public int compareTo(Long other) { return AggregateRoot.super.compareTo(other); }

    @Override
    public Long identity() { return this.id;}

    @Override
    public boolean hasIdentity(Long id) { return AggregateRoot.super.hasIdentity(id); }
}
