package eapli.base.exam.domain.regular_exam;

import eapli.base.course.domain.Course;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamDate;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamSpecification;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="REGULAREXAM")
public class RegularExam implements AggregateRoot<Integer> {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDREGULAREXAM")
    private int id;

    @Column(name = "REGULAREXAMDESCRIPTION")
    private RegularExamSpecification regularExamSpecification;
    @Column(name = "REGULAREXAMDATE")
    private RegularExamDate regularExamDate;

    @JoinColumn(name = "COURSE")
    @ManyToOne
    private Course course;

    public RegularExam(String regularExamSpecification, Date openDate, Date closeDate, Course course)
    {
        Preconditions.nonNull(regularExamSpecification, "Regular Exam description cannot be null");
        //Preconditions.nonNull(date, "Regular Exam date cannot be null");

        this.regularExamSpecification = new RegularExamSpecification(regularExamSpecification);
        this.regularExamDate = new RegularExamDate(openDate, closeDate);
        this.course = course;
    }

    protected RegularExam() {
        this.regularExamSpecification = null;
        this.regularExamDate = null;
    }


    protected RegularExamSpecification regularExamSpecification() {
        return this.regularExamSpecification;
    }
    public RegularExamDate regularExamDate(){return this.regularExamDate;}
    public Course course(){return this.course;}


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


}
