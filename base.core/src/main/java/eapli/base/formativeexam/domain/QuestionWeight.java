package eapli.base.formativeexam.domain;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

/**
 * QuestionWeight
 */
@Embeddable
public class QuestionWeight implements ValueObject {
    private final int weight;

    private QuestionWeight(int weight) {
        Preconditions.nonNegative(weight, "Weight cannot be negative");
        this.weight = weight;
    }

    public static QuestionWeight valueOf(int weight) {
        return new QuestionWeight(weight);
    }

    public static QuestionWeight valueOf(QuestionWeight weight) {
        return new QuestionWeight(weight.weight);
    }

    protected QuestionWeight() {
        this.weight = 0;
    }

    @Override
    public String toString() {
        return Integer.toString(this.weight);
    }
}
