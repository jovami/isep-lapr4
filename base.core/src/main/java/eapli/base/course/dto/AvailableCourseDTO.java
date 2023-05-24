package eapli.base.course.dto;

import eapli.base.course.domain.CourseDescription;
import eapli.base.course.domain.CourseID;
import eapli.base.course.domain.CourseState;

/**
 * AvailableCourseDTO
 */
public final class AvailableCourseDTO {

    private final CourseID id;
    private final CourseDescription desc;
    private final CourseState state;

    public AvailableCourseDTO(CourseID courseId, CourseDescription description, CourseState state) {
        this.id = courseId;
        this.desc = description;
        this.state = state;
    }

    public CourseID courseId() {
        return this.id;
    }

    public CourseDescription desc() {
        return this.desc;
    }

    public CourseState state() {
        return this.state;
    }

    @Override
    public String toString() {
        return String.format("%s, %s (%s)", this.id, this.desc, this.state);
    }
}
