package eapli.base.formativeexam.domain;

import javax.persistence.*;

import eapli.base.course.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

/**
 * FormativeExam
 */
@Entity
public class FormativeExam implements AggregateRoot<FormativeExamTitle> {

    @EmbeddedId
    private FormativeExamTitle title;

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

    protected FormativeExam(FormativeExamTitle title, Course course, FormativeExamSpecification spec) {
        Preconditions.noneNull(title, course, spec);

        this.title = title;
        this.course = course;
        this.spec = spec;
    }

    public FormativeExamSpecification specification() {
        return this.spec;
    }

    public Course course() {
        return this.course;
    }

    public FormativeExamTitle title() {
        return this.title;
    }

    @Override
    public boolean sameAs(Object other) {
        if (this == other)
            return true;
        else if (!(other instanceof FormativeExam))
            return false;

        var o = (FormativeExam) other;
        return this.title.equals(o.title)
                && this.spec.equals(o.spec)
                && this.course.sameAs(o.course);
    }

    @Override
    public FormativeExamTitle identity() {
        return this.title;
    }
}
