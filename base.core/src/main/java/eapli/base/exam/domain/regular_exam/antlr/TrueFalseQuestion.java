package eapli.base.exam.domain.regular_exam.antlr;

import eapli.base.exam.domain.question.QuestionType;
import lombok.Getter;

// @Accessors(fluent = true)
@Getter
public class TrueFalseQuestion extends Question {
    private final String description;

    public TrueFalseQuestion(String description) {
        super(QuestionType.TRUE_FALSE);
        this.description = description;
    }

    public TrueFalseQuestion(Long id, String description) {
        super(id, QuestionType.TRUE_FALSE);
        this.description = description;
    }
}
