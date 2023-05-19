package eapli.base.exam.domain.regular_exam;

import eapli.base.course.domain.Course;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamDate;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamSpecification;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;

@Entity
@Table(name="REGULAREXAM")
public class RegularExam implements AggregateRoot<Integer> {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDREGULAREXAM")
    private int id;

    @Embedded
    @Column(unique = true, nullable = false)
    private RegularExamSpecification regularExamSpecification;
    @Column(name = "REGULAREXAMDATE")
    private RegularExamDate regularExamDate;

    @JoinColumn(name = "COURSE")
    @ManyToOne
    private Course course;

    protected RegularExam() {
        this.regularExamSpecification = null;
        this.regularExamDate = null;
        this.course = null;
    }

    public RegularExam(RegularExamSpecification regularExamSpecification, RegularExamDate regularExamDate, Course course)
    {

        this.regularExamSpecification = regularExamSpecification;
        this.regularExamDate = regularExamDate;
        this.course = course;
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
    public int compareTo(Integer other) {return AggregateRoot.super.compareTo(other);}

    @Override
    public Integer identity() {return this.id;}

    @Override
    public boolean hasIdentity(Integer id) {return AggregateRoot.super.hasIdentity(id);}


    public Course course() {
        return this.course;
    }

    public RegularExamDate regularExamDate(){
        return this.regularExamDate;
    }

    public RegularExamSpecification regularExamSpecification()
    {
        return this.regularExamSpecification;
    }

    @Override
    public String toString() {
        return "RegularExam [" + "ID: " + id + " | " + regularExamDate + "]";
    }
}
