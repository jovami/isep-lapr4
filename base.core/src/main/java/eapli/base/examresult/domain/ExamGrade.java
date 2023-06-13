package eapli.base.examresult.domain;

import java.util.Objects;

import javax.persistence.Embeddable;

import eapli.framework.validations.Preconditions;
import lombok.Getter;
import lombok.experimental.Accessors;

@Embeddable
@Accessors(fluent = true)
@Getter
public class ExamGrade {
    private final Float grade;
    private final Float maxGrade;

    protected ExamGrade() {
        // for ORM
        this.grade = null;
        this.maxGrade = null;
    }

    protected ExamGrade(float grade, float maxGrade) {
        Preconditions.noneNull(grade, maxGrade);
        Preconditions.ensure(grade >= 0.f, "Grade cannot be negative");
        Preconditions.ensure(maxGrade >= 0.f, "Maximum grade cannot be negative");
        Preconditions.ensure(grade <= maxGrade, "Grade cannot be higher than maximum grade");

        this.grade = grade;
        this.maxGrade = maxGrade;
    }

    public static ExamGrade valueOf(float grade, float maxGrade) {
        return new ExamGrade(grade, maxGrade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExamGrade that = (ExamGrade) o;
        return Float.compare(this.grade, that.grade) == 0
                && Float.compare(this.maxGrade, that.maxGrade) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.grade, this.maxGrade);
    }

    @Override
    public String toString() {
        return String.format("Exam grade: %.2f / %.2f\n", this.grade, this.maxGrade);
    }
}
