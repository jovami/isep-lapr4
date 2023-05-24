package eapli.base.course.domain;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

@Embeddable
public class CourseCapacity implements ValueObject {
    private final Integer min;
    private final Integer max;

    protected CourseCapacity() {
        this.max = this.min = null;
    }

    protected CourseCapacity(int min, int max) {
        Preconditions.isPositive(min, "Minimum capacity must be positive");
        Preconditions.isPositive(max, "Maximum capacity must be positive");
        Invariants.ensure(max >= min,
                "Maximum capacity must be greater or equal to minimum capacity");

        this.min = min;
        this.max = max;
    }

    public static CourseCapacity valueOf(int min, int max) {
        return new CourseCapacity(min, max);
    }

    public int maximum() {
        return this.max;
    }

    public int minimum() {
        return this.min;
    }

    @Override
    public String toString() {
        return "Min students enrolled: " + min + "\nMax students enrolled: " + max;
    }
}
