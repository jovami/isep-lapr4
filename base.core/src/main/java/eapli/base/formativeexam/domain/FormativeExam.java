package eapli.base.formativeexam.domain;

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
 * FormativeExam
 */
@Entity
public class FormativeExam implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    @Column(unique = true, nullable = false)
    private final FormativeExamSpecification spec;

    @ManyToOne
    @JoinColumn(nullable = false)
    private final Course course;

    // For ORM
    protected FormativeExam() {
        this.spec = null;
        this.course = null;
    }

    protected FormativeExam(Course course, FormativeExamSpecification spec) {
        Preconditions.noneNull(course, spec);

        this.course = course;
        this.spec = spec;
    }

    public FormativeExamSpecification specification() {
        return this.spec;
    }

    @Override
    public boolean sameAs(Object other) {
        if (this == other)
            return true;
        else if (!(other instanceof FormativeExam))
            return false;

        var o = (FormativeExam) other;
        return this.spec.equals(o.spec)
                && this.course.sameAs(o.course);
    }

    @Override
    public Long identity() {
        return this.id;
    }
}
