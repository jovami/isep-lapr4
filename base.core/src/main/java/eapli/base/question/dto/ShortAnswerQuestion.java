package eapli.base.question.dto;

import eapli.base.question.domain.QuestionType;
import lombok.Getter;

// @Accessors(fluent = true)
@Getter
public class ShortAnswerQuestion extends AbstractQuestionDTO {
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
