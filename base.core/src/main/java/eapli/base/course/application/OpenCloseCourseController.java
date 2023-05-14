package eapli.base.course.application;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

import eapli.base.course.domain.Course;
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
public final class OpenCloseCourseController {
    private final CourseRepository repo;
    private final ListCoursesService svc;

    public OpenCloseCourseController() {
        this.repo = PersistenceContext.repositories().courses();
        this.svc = new ListCoursesService(this.repo);
    }

    private List<CourseAndStateDTO> getCourses(Supplier<Iterable<Course>> courses) {
        return new CourseAndStateDTOMapper().toDTO(courses.get(), Comparator.comparing(Course::identity));
    }

    private Course fromDTO(CourseAndStateDTO dto) throws ConcurrencyException {
        return this.repo.ofIdentity(dto.courseId())
                        .orElseThrow(() -> new ConcurrencyException("Course no longer exists"));
    }

    // Available to the UIs

    public List<CourseAndStateDTO> openableCourses() {
        return this.getCourses(this.svc::openable);
    }

    public List<CourseAndStateDTO> closableCourses() {
        return this.getCourses(this.svc::closable);
    }

    public Either<String, CourseState> openCourse(CourseAndStateDTO chosen) {
        var course = this.fromDTO(chosen);

        var result = course.open();
        this.repo.save(course);
        return result;
    }

    public Either<String, CourseState> closeCourse(CourseAndStateDTO chosen) {
        var course = this.fromDTO(chosen);

        var result = course.close();
        this.repo.save(course);
        return result;
    }
}
