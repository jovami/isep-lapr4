package eapli.base.course.dto;

import java.util.Objects;

import eapli.base.course.domain.CourseID;
import eapli.base.course.domain.CourseState;

/**
 * CourseAndStateDTO
 */
public class CourseAndStateDTO {

    private final CourseID id;
    private final CourseState state;

    public CourseAndStateDTO(CourseID courseId, CourseState state) {
        this.id = courseId;
        this.state = state;
    }

    public CourseID courseId() {
        return this.id;
    }

    public CourseState state() {
        return this.state;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.id, this.state);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null || obj.getClass() != this.getClass())
            return false;
        var o = (CourseAndStateDTO) obj;
        return this.id.equals(o.id) && this.state == o.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.state);
    }
}
