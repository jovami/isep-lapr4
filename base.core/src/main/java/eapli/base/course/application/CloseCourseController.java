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
 * CloseCourseController
 */
public final class CloseCourseController {
    private final CourseRepository repo;

    public CloseCourseController() {
        this.repo = PersistenceContext.repositories().courses();
    }

    public List<CourseAndStateDTO> getCourses() {
        return CourseAndStateDTOMapper.toDTO(new ListCoursesService(repo).closable());
    }

    public Either<String, CourseState> closeCourse(CourseAndStateDTO chosen) {
        var course = this.repo
            .ofIdentity(chosen.courseId())
            .orElseThrow(() -> new ConcurrencyException("Course no longer exists"));

        var result = course.close();
        this.repo.save(course);
        return result;
    }
}
