package eapli.base.examresult.domain;

import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.util.Objects;

@Deprecated
@Embeddable
public class ExamFeedback {

    private String feedback;

    protected ExamFeedback() {
        // for ORM
    }

    protected ExamFeedback(String feedback) {
        Preconditions.nonNull(feedback, "Feedback cannot be null");

        this.feedback = feedback;
    }

    public static ExamFeedback valueOf(String feedback) {
        return new ExamFeedback(feedback);
    }

    public String feedback() {
        return this.feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExamFeedback that = (ExamFeedback) o;
        return Objects.equals(feedback, that.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.feedback);
    }

    @Override
    public String toString() {
        return String.format("Exam Feedback: %s\n", this.feedback);
    }
}
