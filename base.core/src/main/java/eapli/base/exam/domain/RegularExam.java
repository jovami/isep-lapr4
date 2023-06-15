package eapli.base.exam.domain;

import javax.persistence.*;

import eapli.base.course.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;

@Entity
public class RegularExam implements AggregateRoot<RegularExamTitle> {
    @EmbeddedId
    private final RegularExamTitle title;
    @Embedded
    @Column(unique = true, nullable = false)
    private RegularExamSpecification specification;
    @Column(nullable = false)
    private RegularExamDate date;
    @ManyToOne
    private final Course course;

    protected RegularExam() {
        this.title = null;
        this.specification = null;
        this.date = null;
        this.course = null;
    }

    protected RegularExam(RegularExamTitle title, RegularExamSpecification specification, RegularExamDate date,
            Course course) {
        Preconditions.noneNull(title, specification, date, course);

        this.title = title;
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

    public RegularExamTitle title() {
        return this.title;
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
    public int compareTo(RegularExamTitle other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public RegularExamTitle identity() {
        return this.title;
    }

    @Override
    public boolean hasIdentity(RegularExamTitle title) {
        return AggregateRoot.super.hasIdentity(title);
    }

    @Override
    public String toString() {
        return "RegularExam [" + "Title: " + title + " | " + date + "]";
    }
}
