package eapli.base.course.dto;

import eapli.base.course.domain.CourseDescription;
import eapli.base.course.domain.CourseName;
import eapli.base.course.domain.CourseState;

/**
 * AvailableCourseDTO
 */
public final class AvailableCourseDTO {

    private final int id;
    private final CourseName name;
    private final CourseDescription desc;
    private final CourseState state;

    public AvailableCourseDTO(int courseId, CourseName name, CourseDescription description, CourseState state) {
        this.id = courseId;
        this.name = name;
        this.desc = description;
        this.state = state;
    }

    public int courseId() {
        return this.id;
    }

    public CourseName name() {
        return this.name;
    }

    public CourseDescription desc() {
        return this.desc;
    }

    public CourseState state() {
        return this.state;
    }

    @Override
    public String toString() {
        return String.format("%s, %s (%s)", this.name, this.desc, this.state);
    }
}
