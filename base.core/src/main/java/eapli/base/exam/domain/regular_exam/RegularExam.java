package eapli.base.exam.domain.regular_exam;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import eapli.base.course.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

@Entity
public class RegularExam implements AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Embedded
    @Column(unique = true, nullable = false)
    private RegularExamSpecification regularExamSpecification;
    private RegularExamDate regularExamDate;
    @ManyToOne
    private Course course;

    protected RegularExam() {
        this.regularExamSpecification = null;
        this.regularExamDate = null;
        this.course = null;
    }

    public RegularExam(RegularExamSpecification regularExamSpecification, RegularExamDate regularExamDate,
            Course course) {

        this.regularExamSpecification = regularExamSpecification;
        this.regularExamDate = regularExamDate;
        this.course = course;
    }

    public Course course() {
        return this.course;
    }

    public RegularExamDate regularExamDate() {
        return this.regularExamDate;
    }

    public RegularExamSpecification regularExamSpecification() {
        return this.regularExamSpecification;
    }

    public void updateRegularExamDate(RegularExamDate regularExamDate) {
        this.regularExamDate = regularExamDate;
    }

    public void updateRegularExamSpecification(RegularExamSpecification regularExamSpecification) {
        this.regularExamSpecification = regularExamSpecification;
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
        return "RegularExam [" + "ID: " + id + " | " + regularExamDate + "]";
    }
}
