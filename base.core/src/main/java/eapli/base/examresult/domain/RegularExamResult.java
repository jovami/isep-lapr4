package eapli.base.examresult.domain;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;

/**
 * Due to some troubles with the database, I decided to create a class for the REGULAR exam result, instead of having
 * a single class for both regular and formative exam results.
 * Regarding the formative exams, we should discuss how to implement their results system, if in a separate class or
 * in the same class as the regular exams.
 *
 * After that decision, I will update the ListExamResultsService to also work with Formative Exams.
 */
@Entity
public class RegularExamResult implements AggregateRoot<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private ExamGrade grade;

    @Embedded
    private ExamFeedback feedback;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExamGradeProperties gradeProperties;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExamFeedbackProperties feedbackProperties;

    @OneToOne
    @JoinColumn(nullable = false)
    private Student student;

    @OneToOne
    @JoinColumn
    private RegularExam regularExam;

    //TODO: do we really need AnswerGiven from the domain model?

    protected RegularExamResult() {
        // for ORM
    }

    //TODO: create an exam interface and make regular and formative exams implement it
    public RegularExamResult(Student student, RegularExam regularExam, ExamGrade grade, ExamFeedback feedback, ExamGradeProperties gradeProperties, ExamFeedbackProperties feedbackProperties) {
        Preconditions.noneNull(student, regularExam, grade, feedback, gradeProperties, feedbackProperties);

        this.student = student;
        this.regularExam = regularExam;
        this.grade = grade;
        this.feedback = feedback;
        this.gradeProperties = gradeProperties;
        this.feedbackProperties = feedbackProperties;
    }

    public ExamGrade grade() {
        return this.grade;
    }

    public ExamFeedback feedback() {
        return this.feedback;
    }

    public ExamGradeProperties gradeProperties() {
        return this.gradeProperties;
    }

    public ExamFeedbackProperties feedbackProperties() {
        return this.feedbackProperties;
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
                && this.feedback.equals(o.feedback)
                && this.gradeProperties.equals(o.gradeProperties)
                && this.feedbackProperties.equals(o.feedbackProperties)
                && this.student.sameAs(o.student)
                && this.regularExam.sameAs(o.regularExam);
    }

    @Override
    public Long identity() {
        return this.id;
    }
}
