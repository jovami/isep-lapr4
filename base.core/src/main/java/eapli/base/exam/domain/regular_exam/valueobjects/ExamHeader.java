package eapli.base.exam.domain.regular_exam.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class ExamHeader implements ValueObject {

    private static final long serialVersionUID = 1L;

    private String examHeader;

    protected ExamHeader(String examHeader) {
        Preconditions.nonEmpty(examHeader, "Exam header should not be empty or null");

        //TODO: verify regex
        Invariants.ensure(examHeader.matches("[A-Z]"), "Invalid exam header");

        this.examHeader = examHeader;
    }

    //for ORM
    protected ExamHeader() {
        examHeader = null;
    }

    public static ExamHeader valueOf(final String examHeader) {
        return new ExamHeader(examHeader);
    }

    @Override
    public String toString() {
        return examHeader;
    }

}