package eapli.base.course.domain;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

@Embeddable
public class CourseDescription implements ValueObject {

    private String description;

    protected CourseDescription() {
        description = null;
    }

    protected CourseDescription(String description) {
        Preconditions.nonNull(description, "Course description should not be null");
        Preconditions.nonEmpty(description, "Course description should not be empty");

        this.description = description;
    }

    public static CourseDescription valueOf(String description) {
        return new CourseDescription(description);
    }


    @Override
    public String toString() {
        return description;
    }

}
