package eapli.base.course.application;

import java.util.Comparator;
import java.util.List;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.course.dto.CourseAndDescriptionDTO;
import eapli.base.course.dto.CourseAndDescriptionDTOMapper;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.exam.domain.RegularExam;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public final class ListExamsInCourseController {
    private final AuthorizationService authz;

    private final CourseRepository courseRepository;
    private final RegularExamRepository regularExamRepository;
    private final StaffRepository staffRepository;

    public ListExamsInCourseController() {
        this.authz = AuthzRegistry.authorizationService();
        this.courseRepository = PersistenceContext.repositories().courses();
        this.regularExamRepository = PersistenceContext.repositories().regularExams();
        this.staffRepository = PersistenceContext.repositories().staffs();
    }

    public List<CourseAndDescriptionDTO> listCourses() {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.TEACHER);

        var teacher = new MyUserService().currentTeacher();

        return new CourseAndDescriptionDTOMapper().toDTO(this.staffRepository.taughtBy(teacher),
                Comparator.comparing(Course::identity));
    }

    public Iterable<RegularExam> listExams(CourseAndDescriptionDTO chosen) {
        var course = this.courseRepository.ofIdentity(chosen.courseId())
                .orElseThrow(() -> new ConcurrencyException("Course no longer exists"));

        return this.regularExamRepository.findByCourse(course);
    }
}
