package eapli.base.course.dto;

import java.util.Objects;

import eapli.base.course.domain.CourseDescription;
import eapli.base.course.domain.CourseName;

public class CourseAndDescriptionDTO {
    private final int courseId;
    private final CourseName name;
    private final CourseDescription description;

    public CourseAndDescriptionDTO(int id, CourseName name, CourseDescription description) {
        this.courseId = id;
        this.name = name;
        this.description = description;
    }

    public int courseId() {
        return this.courseId;
    }

    public CourseName name() {
        return this.name;
    }

    public CourseDescription description() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.name, this.description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null || obj.getClass() != this.getClass())
            return false;
        var o = (CourseAndDescriptionDTO) obj;
        return this.name.equals(o.name) && this.description.equals(o.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.description);
    }
}
