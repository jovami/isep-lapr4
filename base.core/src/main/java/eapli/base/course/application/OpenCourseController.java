package eapli.base.course.application;

import java.util.List;

import eapli.base.course.domain.CourseState;
import eapli.base.course.dto.CourseAndStateDTO;
import eapli.base.course.dto.CourseAndStateDTOMapper;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.functional.Either;

/**
 * OpenCourseController
 */
public final class OpenCourseController {
    private final CourseRepository repo;

    public OpenCourseController() {
        this.repo = PersistenceContext.repositories().courses();
    }

    public List<CourseAndStateDTO> getCourses() {
        return CourseAndStateDTOMapper.toDTO(new ListCoursesService(repo).openable());
    }

    public Either<String, CourseState> openCourse(CourseAndStateDTO chosen) {
        System.out.println(chosen.courseId());
        var course = this.repo
            .ofIdentity(chosen.courseId())
            .orElseThrow(() -> new ConcurrencyException("Course no longer exists"));

        var result = course.open();
        this.repo.save(course);
        return result;
    }
}
