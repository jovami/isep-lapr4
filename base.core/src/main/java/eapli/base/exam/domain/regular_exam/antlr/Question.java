package eapli.base.exam.domain.regular_exam.antlr;

import eapli.base.exam.domain.question.QuestionType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Question {
    private Long id;
    private QuestionType type;

    public Question (QuestionType type) {
        this(-1L, type);
    }

    public final QuestionType type() {
        return this.type;
    }

    public final Long id() {
        return this.id;
    }
}
