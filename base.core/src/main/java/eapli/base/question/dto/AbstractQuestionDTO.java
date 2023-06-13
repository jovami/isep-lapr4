package eapli.base.question.dto;

import eapli.base.question.domain.QuestionType;
import lombok.AllArgsConstructor;
// import lombok.Getter;

// @Getter
@AllArgsConstructor
public abstract class AbstractQuestionDTO {
    private Long id;
    private QuestionType type;

    public AbstractQuestionDTO (QuestionType type) {
        this(-1L, type);
    }

    public final QuestionType getType() {
        return this.type;
    }

    public final Long getId() {
        return this.id;
    }
}
