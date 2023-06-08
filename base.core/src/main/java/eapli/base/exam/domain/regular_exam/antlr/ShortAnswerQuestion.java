package eapli.base.exam.domain.regular_exam.antlr;

import eapli.base.exam.domain.question.QuestionType;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public class ShortAnswerQuestion extends Question {
    private final String description;

    public ShortAnswerQuestion(String description) {
        super(QuestionType.SHORT_ANSWER);
        this.description = description;
    }

    public ShortAnswerQuestion(Long id, String description) {
        super(id, QuestionType.SHORT_ANSWER);
        this.description = description;
    }
}
