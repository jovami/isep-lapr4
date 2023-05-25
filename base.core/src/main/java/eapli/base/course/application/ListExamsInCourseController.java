package eapli.base.course.application;

import java.util.Comparator;
import java.util.List;

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
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public class ListExamsInCourseController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    CourseRepository courseRepository;
    RegularExamRepository regularExamRepository;
    TeacherRepository teacherRepository;
    StaffRepository staffRepository;

    public ListExamsInCourseController() {
        courseRepository = PersistenceContext.repositories().courses();
        regularExamRepository = PersistenceContext.repositories().regularExams();
        teacherRepository = PersistenceContext.repositories().teachers();
        staffRepository = PersistenceContext.repositories().staffs();
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

        return new CourseAndDescriptionDTOMapper().toDTO(staffRepository.taughtBy(teacher), Comparator.comparing(Course::identity));
    }

    public Iterable<RegularExam> listExams(CourseAndDescriptionDTO chosen){
        var course = this.courseRepository.ofIdentity(chosen.courseId())
                .orElseThrow(() -> new ConcurrencyException("Course no longer exists"));

        return regularExamRepository.findByCourse(course);
    }
}
