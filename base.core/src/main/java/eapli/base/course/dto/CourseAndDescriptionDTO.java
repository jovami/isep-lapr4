package eapli.base.course.dto;

import java.util.Objects;

import eapli.base.course.domain.CourseDescription;
import eapli.base.course.domain.CourseID;

public class CourseAndDescriptionDTO {
    private final CourseID id;
    private final CourseDescription description;

    public CourseAndDescriptionDTO(CourseID name, CourseDescription description) {
        this.id = name;
        this.description = description;
    }

    public CourseID courseId() {
        return this.id;
    }

    public CourseDescription description() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.id, this.description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null || obj.getClass() != this.getClass())
            return false;
        var o = (CourseAndDescriptionDTO) obj;
        return this.id.equals(o.id) && this.description.equals(o.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.description);
    }
}
