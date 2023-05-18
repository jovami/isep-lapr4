package eapli.base.exam.domain.regular_exam;

import eapli.base.course.domain.Course;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamDate;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamHeader;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamTitle;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamHeaderDescription;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="REGULAREXAM")
public class RegularExam implements AggregateRoot<Integer> {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDREGULAREXAM")
    private int id;

    @Column(name = "REGULAREXAMTITLE")
    private RegularExamTitle title;

    @Column(name = "REGULAREXAMHEADER")
    private RegularExamHeader header;

    @Column(name = "REGULAREXAMDESCRIPTION")
    private RegularExamHeaderDescription description;
    @Column(name = "REGULAREXAMDATE")
    private RegularExamDate date;

    @Column(name = "COURSE")
    @ManyToOne
    private Course course;

    @OneToMany
    @Column(name = "REGULAREXAMSECTION")
    private List<RegularExamSection> sections;

    public RegularExam(RegularExamTitle title, RegularExamHeader header, RegularExamHeaderDescription description, RegularExamDate date,
                       List<RegularExamSection> sections, Course course)
    {

        Preconditions.nonNull(title, "Regular Exam title cannot be null");
        Preconditions.nonNull(header, "Regular Exam header cannot be null");
        Preconditions.nonNull(description, "Regular Exam description cannot be null");
        Preconditions.nonNull(date, "Regular Exam date cannot be null");
        Preconditions.nonNull(sections, "Regular Exam sections cannot be null");
        Preconditions.nonEmpty(sections, "Regular Exams must have at least one section");
        Preconditions.noneNull(course, "Course cannot be null");

        this.title = title;
        this.header = header;
        this.description = description;
        this.date = date;
        this.sections = new ArrayList<>(sections);
        this.course = course;
    }

    protected RegularExam() {
        this.title = null;
        this.header = null;
        this.description = null;
        this.date = null;
        this.sections = null;
    }

    protected List<RegularExamSection> sections() {return this.sections;}
    protected RegularExamTitle title() {
        return this.title;
    }
    protected RegularExamHeader header(){return this.header;}
    protected RegularExamHeaderDescription description() {
        return this.description;
    }
    public RegularExamDate date(){return this.date;}
    public Course course() { return this.course; }


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
