package eapli.base.enrollment.aplication;


import eapli.base.course.application.ListCoursesService;
import eapli.base.course.domain.Course;
import eapli.base.course.dto.CourseAndStateDTO;
import eapli.base.course.dto.CourseAndStateDTOMapper;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.ConcurrencyException;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;


public final class OpenCloseEnrollmentController {

    private final CourseRepository repoCourse;

    private final ListCoursesService svc;
    public OpenCloseEnrollmentController() {
        this.repoCourse = PersistenceContext.repositories().courses();
        this.svc = new ListCoursesService(this.repoCourse);
    }

    private List<CourseAndStateDTO> getCourses(Supplier<Iterable<Course>> courses) {
        return new CourseAndStateDTOMapper().toDTO(courses.get(), Comparator.comparing(Course::identity));
    }

    private Course fromDTO(CourseAndStateDTO dto) throws ConcurrencyException {
        return this.repoCourse.ofIdentity(dto.courseId())
                .orElseThrow(() -> new ConcurrencyException("Course no longer exists"));
    }

    public List<CourseAndStateDTO> enrollableCourses() {
        return this.getCourses(this.svc::enrollable);
    }



    public void openEnrollments(CourseAndStateDTO chosen)
    {
        var course = this.fromDTO(chosen);

        course.openEnrollments();
        this.repoCourse.save(course);

    }

    public void closeEnrollments(CourseAndStateDTO chosen)
    {
        var course = this.fromDTO(chosen);

        course.closeEnrollments();
        this.repoCourse.save(course);

    }
}
