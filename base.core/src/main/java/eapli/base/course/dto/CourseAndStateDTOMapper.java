package eapli.base.course.dto;

import eapli.base.course.domain.Course;
import jovami.util.dto.Mapper;

/**
 * CourseAndStateDTOMapper
 */
public final class CourseAndStateDTOMapper implements Mapper<Course, CourseAndStateDTO> {

    @Override
    public CourseAndStateDTO toDTO(Course course) {
        return new CourseAndStateDTO(course.identity(), course.state());
    }
}
