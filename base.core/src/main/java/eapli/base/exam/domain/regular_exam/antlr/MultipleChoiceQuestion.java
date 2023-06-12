package eapli.base.exam.domain.regular_exam.antlr;

import eapli.base.exam.domain.question.QuestionType;
import lombok.Getter;

import java.util.List;

// @Accessors(fluent = true)
@Getter
public class MultipleChoiceQuestion extends Question {
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
