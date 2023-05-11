package eapli.base.exam.domain.regular_exam;

import eapli.base.exam.domain.regular_exam.valueobjects.ExamDate;
import eapli.base.exam.domain.regular_exam.valueobjects.ExamHeader;
import eapli.base.exam.domain.regular_exam.valueobjects.ExamTitle;
import eapli.base.exam.domain.regular_exam.valueobjects.HeaderDescription;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="REGULAREXAM")
public class RegularExam implements AggregateRoot<Integer> {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDExam")
    private int id;

    @Column(name = "EXAMTITLE")
    private ExamTitle examTitle;

    @Column(name = "EXAMHEADER")
    private ExamHeader examHeader;

    @Column(name = "EXAMDESCRIPTION")
    private HeaderDescription headerDescription;
    @Column(name = "EXAMDATE")
    private ExamDate examDate;

    @OneToMany
    @Column(name = "REGULAREXAMSECTION")
    private List<RegularExamSection> regularExamSections;

    protected RegularExam(ExamTitle examTitle, ExamHeader examHeader,HeaderDescription headerDescription, ExamDate examDate)
    {
        this.examTitle = examTitle;
        this.examHeader = examHeader;
        this.headerDescription = headerDescription;
        this.examDate = examDate;
    }

    //JPA needs empty constructor
    public RegularExam() {
        this.examTitle = new ExamTitle();
        this.examHeader = new ExamHeader();
        this.headerDescription = new HeaderDescription();
        this.examDate = new ExamDate();
    }


    public void setTitle(String examTitle){this.examTitle = this.examTitle.valueOf(examTitle);}

    public void setHeader(String examHeader){this.examHeader = this.examHeader.valueOf(examHeader);}

    public void setHeaderDescription(String headerDescription)
    {
        this.headerDescription = this.headerDescription.valueOf(headerDescription);
    }

    public void setExamDate(Date openDate, Date closeDate)
    {
        this.examDate = this.examDate.valueOf(openDate,closeDate);
    }

    public void addListOfSectionsToExam(List<RegularExamSection> regularExamSections)
    {
        this.regularExamSections = regularExamSections;
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

    @Override
    public String toString() {
        return "RegularExam{" +
                "id=" + id +
                ", examTitle=" + examTitle +
                ", examHeader=" + examHeader +
                ", headerDescription=" + headerDescription +
                ", examDate=" + examDate +
                '}';
    }
}
