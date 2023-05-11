package eapli.base.exam.domain.question.valueobjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;



public class QuestionFeedback implements ValueObject {

    private String questionFeedback;

    protected QuestionFeedback() {}

    protected QuestionFeedback(String questionFeedback)
    {
        Preconditions.nonNull(questionFeedback);

        this.questionFeedback = questionFeedback;
    }

    public static QuestionFeedback valueOf(String questionFeedback) {return new QuestionFeedback(questionFeedback);}

    @Override
    public String toString() {
        return questionFeedback;
    }

}
