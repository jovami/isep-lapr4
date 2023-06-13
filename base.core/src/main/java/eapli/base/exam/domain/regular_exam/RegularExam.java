package eapli.base.exam.domain.regular_exam;

import javax.persistence.*;

import eapli.base.course.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;

@Entity
public class RegularExam implements AggregateRoot<Integer> {
    // TODO: unique ExamTitle to identify the exam
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Embedded
    @Column(unique = true, nullable = false)
    private RegularExamSpecification specification;
    @Column(nullable = false)
    private RegularExamDate date;
    @ManyToOne
    private final Course course;

    protected RegularExam() {
        this.specification = null;
        this.date = null;
        this.course = null;
    }

    protected RegularExam(RegularExamSpecification specification, RegularExamDate date,
            Course course) {
        Preconditions.noneNull(specification, date, course);

        this.specification = specification;
        this.date = date;
        this.course = course;
    }

    public Course course() {
        return this.course;
    }

    public RegularExamDate date() {
        return this.date;
    }

    public RegularExamSpecification specification() {
        return this.specification;
    }

    public void updateDate(RegularExamDate regularExamDate) {
        this.date = regularExamDate;
    }

    protected void updateSpecification(RegularExamSpecification regularExamSpecification) {
        this.specification = regularExamSpecification;
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
    public int compareTo(Integer other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Integer identity() {
        return this.id;
    }

    @Override
    public boolean hasIdentity(Integer id) {
        return AggregateRoot.super.hasIdentity(id);
    }

    @Override
    public String toString() {
        return "RegularExam [" + "ID: " + id + " | " + date + "]";
    }
}
