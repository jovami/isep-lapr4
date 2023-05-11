package eapli.base.formativeexam.domain;

import javax.persistence.Embeddable;

import eapli.base.exam.domain.question.Question;
import eapli.framework.domain.model.ValueObject;

/**
 * FormativeExamQuestion
 */
@Embeddable
public class FormativeExamQuestion implements ValueObject {
    private final Question question;
    private final QuestionWeight weight;

    protected FormativeExamQuestion() {
        this.question = null;
        this.weight = null;
    }

    public FormativeExamQuestion(Question question, QuestionWeight weight) {
        this.question = question;
        this.weight = QuestionWeight.valueOf(weight);
    }
}
