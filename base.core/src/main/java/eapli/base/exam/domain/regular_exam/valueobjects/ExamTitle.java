package eapli.base.exam.domain.regular_exam.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class ExamTitle implements ValueObject {

    private String examTitle;

    protected ExamTitle(String examTitle) {
        Preconditions.nonEmpty(examTitle, "Exam tittle should not be empty or null");

        //TODO: verify regex
        Invariants.ensure(examTitle.matches("[A-Z]"), "Invalid exam tittle");

        this.examTitle = examTitle;
    }

    //for ORM
    public ExamTitle() {
        examTitle = null;
    }

    public static ExamTitle valueOf(final String examTitle) {return new ExamTitle(examTitle);}

    @Override
    public String toString() {
        return examTitle;
    }
}
