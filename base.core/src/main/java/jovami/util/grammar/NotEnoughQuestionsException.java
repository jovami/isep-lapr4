package jovami.util.grammar;

import eapli.base.question.domain.QuestionType;

/**
 * NotEnoughQuestionsException
 */
public final class NotEnoughQuestionsException extends RuntimeException {

    public NotEnoughQuestionsException(QuestionType type) {
        this("Not enough questions of type " + type + "to fulfill formative exam");
    }

    public NotEnoughQuestionsException() {
        this("Not enough questions to fulfill formative exam");
    }

    public NotEnoughQuestionsException(String message) {
        super(message);
    }

}
