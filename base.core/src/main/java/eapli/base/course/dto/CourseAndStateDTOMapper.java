package eapli.base.course.dto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import eapli.base.course.domain.Course;

/**
 * CourseAndStateDTOMapper
 */
public final class CourseAndStateDTOMapper {

    public static CourseAndStateDTO toDTO(Course course) {
        return new CourseAndStateDTO(course.identity(), course.getName(), course.state());
    }

    public static List<CourseAndStateDTO> toDTO(Iterable<Course> courses) {
        return StreamSupport.stream(courses.spliterator(), false)
                            .sorted(Comparator.comparing(Course::identity))
                            .map(CourseAndStateDTOMapper::toDTO)
                            .collect(Collectors.toList());
    }
}
