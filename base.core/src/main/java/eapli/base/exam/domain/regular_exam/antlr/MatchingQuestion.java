package eapli.base.exam.domain.regular_exam.antlr;

import eapli.base.exam.domain.question.QuestionType;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)
@Getter
public class MatchingQuestion extends Question {
    private final String description;
    private final List<String> phrase1;
    private final List<String> phrase2;

    public MatchingQuestion(String description, List<String> phrase1, List<String> phrase2) {
        super(QuestionType.MATCHING);
        this.description = description;
        this.phrase1 = phrase1;
        this.phrase2 = phrase2;
    }

    public MatchingQuestion(Long id, String description, List<String> phrase1, List<String> phrase2) {
        super(id, QuestionType.MATCHING);
        this.description = description;
        this.phrase1 = phrase1;
        this.phrase2 = phrase2;
    }
}
