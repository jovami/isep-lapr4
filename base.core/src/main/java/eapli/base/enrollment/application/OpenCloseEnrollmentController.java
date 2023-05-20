package eapli.base.enrollment.application;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

import eapli.base.course.domain.Course;
import eapli.base.course.dto.CourseAndStateDTO;
import eapli.base.course.dto.CourseAndStateDTOMapper;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;

@UseCaseController
public final class OpenCloseEnrollmentController {

    private final CourseRepository repoCourse;
    public OpenCloseEnrollmentController() {
        this.repoCourse = PersistenceContext.repositories().courses();
    }

    private List<CourseAndStateDTO> getCourses(Supplier<Iterable<Course>> courses) {
        return new CourseAndStateDTOMapper().toDTO(courses.get(), Comparator.comparing(Course::identity));
    }

    private Course fromDTO(CourseAndStateDTO dto) throws ConcurrencyException {
        return this.repoCourse.ofIdentity(dto.courseId())
                .orElseThrow(() -> new ConcurrencyException("Course no longer exists"));
    }

    public List<CourseAndStateDTO> openableToEnrollmentsCourses() {
        return this.getCourses(this.repoCourse::openableToEnrollments);
    }

    public List<CourseAndStateDTO> enrollableCourses() {
        return this.getCourses(this.repoCourse::enrollable);
    }

    public void openEnrollments(CourseAndStateDTO chosen) {
        var course = this.fromDTO(chosen);

        course.openEnrollments();
        this.repoCourse.save(course);
    }

    public void closeEnrollments(CourseAndStateDTO chosen) {
        var course = this.fromDTO(chosen);

        course.closeEnrollments();
        this.repoCourse.save(course);
    }
}
