package eapli.base.question.dto;

import java.util.List;

import eapli.base.question.domain.QuestionType;
import lombok.Getter;

// @Accessors(fluent = true)
@Getter
public class MultipleChoiceQuestion extends AbstractQuestionDTO {
    private final boolean singleAnswer;
    private final String description;
    private final List<String> options;

    public MultipleChoiceQuestion(boolean singleAnswer, String description, List<String> options) {
        super(QuestionType.MULTIPLE_CHOICE);
        this.singleAnswer = singleAnswer;
        this.description = description;
        this.options = options;
    }

    public MultipleChoiceQuestion(Long id, boolean singleAnswer, String description, List<String> options) {
        super(id, QuestionType.MULTIPLE_CHOICE);
        this.singleAnswer = singleAnswer;
        this.description = description;
        this.options = options;
    }
}
