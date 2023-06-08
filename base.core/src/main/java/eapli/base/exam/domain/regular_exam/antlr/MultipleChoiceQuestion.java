package eapli.base.exam.domain.regular_exam.antlr;

import eapli.base.exam.domain.question.QuestionType;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)
@Getter
public class MultipleChoiceQuestion extends Question {
    private final String description;
    private final List<String> options;

    public MultipleChoiceQuestion(String description, List<String> options) {
        super(QuestionType.MULTIPLE_CHOICE);
        this.description = description;
        this.options = options;
    }

    public MultipleChoiceQuestion(Long id, String description, List<String> options) {
        super(id, QuestionType.MULTIPLE_CHOICE);
        this.description = description;
        this.options = options;
    }
}
