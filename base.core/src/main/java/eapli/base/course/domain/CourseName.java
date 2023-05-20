package eapli.base.course.domain;

import java.util.Objects;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

@Embeddable
public class CourseName implements ValueObject {

    private final String name;

    protected CourseName() {
        name = null;
    }

    protected CourseName(final String name) {
        Preconditions.noneNull(name, "Course name should not be null");
        Preconditions.nonEmpty(name, "Course name should not be empty");

        this.name = name;
    }

    public static CourseName valueOf(final String name) {
        return new CourseName(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CourseName that = (CourseName) o;
        return this.name.equals(that.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

    public String name() {
        return this.name;
    }
}
