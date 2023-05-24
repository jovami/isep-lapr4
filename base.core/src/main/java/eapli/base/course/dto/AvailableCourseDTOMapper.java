package eapli.base.course.dto;

import eapli.base.course.domain.Course;
import jovami.util.dto.Mapper;

/**
 * AvailableCourseDTOMapper
 */
public class AvailableCourseDTOMapper implements Mapper<Course, AvailableCourseDTO> {

    @Override
    public AvailableCourseDTO toDTO(Course c) {
        return new AvailableCourseDTO(c.identity(), c.description(), c.state());
    }
}
