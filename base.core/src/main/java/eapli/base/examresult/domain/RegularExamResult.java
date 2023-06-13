package eapli.base.examresult.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

@Entity
public class RegularExamResult implements AggregateRoot<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private final ExamGrade grade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private final ExamGradeProperties gradeProperties;

    @OneToOne
    @JoinColumn(nullable = false)
    private final Student student;

    @OneToOne
    @JoinColumn(nullable = false)
    private final RegularExam regularExam;

    // for ORM
    protected RegularExamResult() {
        this.grade = null;
        this.gradeProperties = null;
        this.student = null;
        this.regularExam = null;
    }

    public RegularExamResult(Student student, RegularExam regularExam,
            ExamGrade grade, ExamGradeProperties gradeProperties) {
        Preconditions.noneNull(student, regularExam, grade, gradeProperties);

        this.student = student;
        this.regularExam = regularExam;
        this.grade = grade;
        this.gradeProperties = gradeProperties;
    }

    public ExamGrade grade() {
        return this.grade;
    }

    public ExamGradeProperties gradeProperties() {
        return this.gradeProperties;
    }

    public Student student() {
        return this.student;
    }

    public RegularExam regularExam() {
        return this.regularExam;
    }

    @Override
    public boolean sameAs(Object other) {
        if (this == other)
            return true;
        else if (!(other instanceof RegularExamResult))
            return false;

        var o = (RegularExamResult) other;

        return this.grade.equals(o.grade)
                && this.gradeProperties.equals(o.gradeProperties)
                && this.student.sameAs(o.student)
                && this.regularExam.sameAs(o.regularExam);
    }

    @Override
    public Long identity() {
        return this.id;
    }
}
