package eapli.base.examresult.domain;

import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ExamGrade {
    private float grade;

    protected ExamGrade() {
        // for ORM
    }

    protected ExamGrade(float grade) {
        Preconditions.nonNull(grade, "Grade cannot be null");

        this.grade = grade;
    }

    public static ExamGrade valueOf(float grade) {
        return new ExamGrade(grade);
    }
    public float grade() {
        return this.grade;
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
        return Objects.equals(grade, that.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.grade);
    }

    @Override
    public String toString() {
        return String.format("Exam grade: %s\n", this.grade);
    }
}
