package eapli.base.exam.domain.question;

import eapli.base.exam.domain.question.valueobjects.QuestionFeedback;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "questionId")
    private Long id;

    @Version
    private Long version;

    @Embedded
    private String description;

    @Embedded
    private String solution;

    @Embedded
    private QuestionType questionType;

    //TODO
    /*@OneToMany
    @Column(name = "QuestionFeedback")
    private List<QuestionFeedback> questionFeedbacks;*/

    public Question(String description, String solution)
    {
        this.description = description;
        this.solution = solution;
    }

    protected Question() {
        // for ORM only.
    }

    public QuestionType type() {
        return this.questionType;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Question)) {
            return false;
        }

        final Question that = (Question) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
    }

    @Override
    public int compareTo(Long other) { return AggregateRoot.super.compareTo(other);}

    @Override
    public Long identity() { return this.id; }

    @Override
    public boolean hasIdentity(Long id) { return AggregateRoot.super.hasIdentity(id); }
}
