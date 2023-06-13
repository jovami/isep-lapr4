package eapli.base.question.dto;

import eapli.base.question.domain.QuestionType;
import lombok.Getter;

// @Accessors(fluent = true)
@Getter
public class TrueFalseQuestion extends AbstractQuestionDTO {
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
