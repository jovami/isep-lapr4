package eapli.base.exam.domain.regular_exam.antlr;

import eapli.base.exam.domain.question.QuestionType;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Accessors(fluent = true)
@Getter
public class MissingWordsQuestion extends Question {
    private final String description;
    private final Map<String, List<String>> groups;
    private final List<String> choices;

    public MissingWordsQuestion(String description, Map<String, List<String>> groups, List<String> choices) {
        super(QuestionType.MISSING_WORDS);
        this.description = description;
        this.groups = groups;
        this.choices = choices;
    }

    public MissingWordsQuestion(Long id, String description, Map<String, List<String>> groups, List<String> choices) {
        super(id, QuestionType.MISSING_WORDS);
        this.description = description;
        this.groups = groups;
        this.choices = choices;
    }
}