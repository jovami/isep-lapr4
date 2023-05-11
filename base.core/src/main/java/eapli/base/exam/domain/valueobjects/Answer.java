package eapli.base.exam.domain.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class Answer implements ValueObject {
    private static final long serialVersionUID = 1L;

    private final String answer;

    protected Answer(String answer) {
        Preconditions.nonEmpty(answer,
                "Exam feedback should neither be null nor empty");

        this.answer = answer;
    }

    // for ORM
    protected Answer() {
        answer = null;
    }

    public static Answer valueOf(final String answer) { return new Answer(answer);}

    @Override
    public String toString() { return answer;}
}
