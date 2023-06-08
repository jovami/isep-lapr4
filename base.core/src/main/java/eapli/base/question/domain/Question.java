package eapli.base.question.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import eapli.base.course.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

/**
 * Question
 */
@Entity
public class Question implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    @Column(unique = true, nullable = false)
    private final QuestionSpecification spec;

    @ManyToOne
    @JoinColumn(nullable = false)
    private final Course course;

    // For ORM
    protected Question() {
        this.spec = null;
        this.course = null;
    }

    protected Question(Course course, QuestionSpecification spec) {
        Preconditions.noneNull(course, spec);

        this.course = course;
        this.spec = spec;
    }

    public QuestionSpecification specification() {
        return this.spec;
    }

    public Course course() {
        return this.course;
    }

    @Override
    public boolean sameAs(Object other) {
        if (this == other)
            return true;
        else if (!(other instanceof Question))
            return false;

        var o = (Question) other;
        return this.spec.equals(o.spec)
                && this.course.sameAs(o.course);
    }

    @Override
    public Long identity() {
        return this.id;
    }
}
