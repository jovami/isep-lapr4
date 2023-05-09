package eapli.base.exam.domain.exam_result;

import eapli.base.exam.domain.valueobjects.Answer;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;

@Entity
public class AnswerGiven implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @Embedded
    private Answer answerGiven;

    protected AnswerGiven(Answer answerGiven)
    {
        Preconditions.nonNull(answerGiven, "Answer given should not be null");

        this.answerGiven = answerGiven;
    }

    protected AnswerGiven () {
        //for ORM only
    }


    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof AnswerGiven)) {
            return false;
        }

        final AnswerGiven that = (AnswerGiven) other;
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
