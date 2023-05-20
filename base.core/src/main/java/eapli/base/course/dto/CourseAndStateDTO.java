package eapli.base.course.dto;

import java.util.Objects;

import eapli.base.course.domain.CourseName;
import eapli.base.course.domain.CourseState;

/**
 * CourseAndStateDTO
 */
public class CourseAndStateDTO {

    private final CourseName name;
    private final CourseState state;
    private final int courseId;

    public CourseAndStateDTO(int id, String name, CourseState state) {
        this.courseId = id;
        this.name = new CourseName(name);
        this.state = state;
    }

    public int courseId() {
        return this.courseId;
    }

    public CourseState state() {
        return this.state;
    }

    public CourseName name() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.name, this.state);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null || obj.getClass() != this.getClass())
            return false;
        var o = (CourseAndStateDTO) obj;
        return this.name.equals(o.name) && this.state == o.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.state);
    }
}
