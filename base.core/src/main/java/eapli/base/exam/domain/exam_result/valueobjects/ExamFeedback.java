package eapli.base.exam.domain.exam_result.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class ExamFeedback implements ValueObject {

    private static final long serialVersionUID = 1L;

    private final String examFeedback;

    protected ExamFeedback(String examFeedback) {
        Preconditions.nonEmpty(examFeedback,
                "Exam feedback should neither be null nor empty");

        this.examFeedback = examFeedback;
    }

    // for ORM
    protected ExamFeedback() {
        examFeedback = null;
    }

    public static ExamFeedback valueOf(final String examFeedback) { return new ExamFeedback(examFeedback);}

    @Override
    public String toString() { return examFeedback;}

}
