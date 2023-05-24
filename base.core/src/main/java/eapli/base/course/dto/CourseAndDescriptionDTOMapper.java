package eapli.base.course.dto;

import eapli.base.course.domain.Course;
import jovami.util.dto.Mapper;

public class CourseAndDescriptionDTOMapper implements Mapper<Course, CourseAndDescriptionDTO> {

    @Override
    public CourseAndDescriptionDTO toDTO(Course course) {
        return new CourseAndDescriptionDTO(course.identity(), course.description());
    }
}
