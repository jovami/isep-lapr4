package eapli.base.course.application;

import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.course.dto.CourseAndDescriptionDTO;
import eapli.base.course.dto.CourseAndDescriptionDTOMapper;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

public class ListExamsInCourseController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    CourseRepository courseRepository;
    RegularExamRepository regularExamRepository;
    TeacherRepository teacherRepository;
    StaffRepository staffRepository;

    public ListExamsInCourseController() {
        courseRepository = PersistenceContext.repositories().courses();
        regularExamRepository = PersistenceContext.repositories().exams();
        teacherRepository = PersistenceContext.repositories().teachers();
    }

    private List<CourseAndDescriptionDTO> getCourses(Iterable<Course> courses) {
        return new CourseAndDescriptionDTOMapper().toDTO(courses, Comparator.comparing(Course::identity));
    }

    private Course fromDTO(CourseAndDescriptionDTO dto) throws ConcurrencyException {
        return this.courseRepository.ofIdentity(dto.courseId())
                .orElseThrow(() -> new ConcurrencyException("Course no longer exists"));
    }

    public List<CourseAndDescriptionDTO> listCourses() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.TEACHER);
        var session = authz.session();
        if (session.isEmpty()) {
            throw new IllegalStateException("Session not found");
        }

        var teacher = teacherRepository.findBySystemUser(session.get().authenticatedUser()).orElseThrow(
                () -> new IllegalStateException("Teacher not found")
        );

        return getCourses(staffRepository.taughtBy(teacher));
    }

    public Iterable<RegularExam> listExams(CourseAndDescriptionDTO chosen){
        var course = fromDTO(chosen);

        return regularExamRepository.findByCourse(course);
    }

}
