package eapli.base.question.dto;

import eapli.base.question.domain.QuestionType;
import lombok.Getter;

// @Accessors(fluent = true)
@Getter
public class NumericalQuestion extends AbstractQuestionDTO {
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
