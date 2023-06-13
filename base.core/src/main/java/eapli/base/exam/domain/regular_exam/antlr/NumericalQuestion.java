package eapli.base.exam.domain.regular_exam.antlr;

import eapli.base.exam.domain.question.QuestionType;
import lombok.Getter;

// @Accessors(fluent = true)
@Getter
public class NumericalQuestion extends Question {
    private final String description;
    public NumericalQuestion(String description) {
        super(QuestionType.NUMERICAL);
        this.description = description;
    }

    public NumericalQuestion(Long id, String description) {
        super(id, QuestionType.NUMERICAL);
        this.description = description;
    }
}
