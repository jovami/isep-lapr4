package eapli.base.examresult.application;

import java.util.Comparator;
import java.util.List;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.course.dto.CourseAndDescriptionDTO;
import eapli.base.course.dto.CourseAndDescriptionDTOMapper;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.examresult.dto.ExamGradeAndStudentDTO;
import eapli.base.examresult.dto.ExamGradeAndStudentDTOMapper;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public class ListExamsGradesInCourseController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final ListExamResultsService svc;

    private final StaffRepository staffRepository;

    private final CourseRepository courseRepository;

    public ListExamsGradesInCourseController() {
        this.staffRepository = PersistenceContext.repositories().staffs();
        this.courseRepository = PersistenceContext.repositories().courses();
        this.svc = new ListExamResultsService();
    }

    public List<CourseAndDescriptionDTO> listCourses() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.TEACHER);

        return new CourseAndDescriptionDTOMapper().toDTO(
                staffRepository.taughtBy(new MyUserService().currentTeacher()),
                Comparator.comparing(Course::identity));
    }

    public List<ExamGradeAndStudentDTO> listExamsGradesAndStudents(CourseAndDescriptionDTO chosen) {
        var course = this.courseRepository.ofIdentity(chosen.courseId())
                .orElseThrow(() -> new ConcurrencyException("Course no longer exists"));

        return new ExamGradeAndStudentDTOMapper().toDTO(this.svc.regularExamResultsBasedOnGradingProperties(course));
    }
}
