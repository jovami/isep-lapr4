package eapli.base.course.domain;

import eapli.base.course.dto.CreateCourseDTO;

/**
 * CourseFactory
 */
public final class CourseFactory {

    public Course build(CreateCourseDTO dto) {
        var id = CourseID.valueOf(dto.title(), dto.code());
        var description = CourseDescription.valueOf(dto.description());
        var duration = CourseDuration.valueOf(dto.startDate(), dto.endDate());
        var capacity = CourseCapacity.valueOf(dto.minCapacity(), dto.maxCapacity());

        return new Course(id, description, duration, capacity);
    }
}
