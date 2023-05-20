package eapli.base.question.application;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.dto.AvailableCourseDTO;
import eapli.base.course.dto.AvailableCourseDTOMapper;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.question.domain.Question;
import eapli.base.question.domain.QuestionFactory;
import eapli.base.question.repositories.QuestionRepository;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 * AddExamQuestionsController
 */
@UseCaseController
public class AddExamQuestionsController {
    private final AuthorizationService authz;
    private final QuestionRepository repo;

    private final CourseRepository courseRepo;
    private final TeacherRepository teacherRepo;
    private final StaffRepository staffRepo;

    public AddExamQuestionsController() {
        var repos = PersistenceContext.repositories();
        this.authz = AuthzRegistry.authorizationService();

        this.repo = repos.questions();

        this.courseRepo = repos.courses();
        this.teacherRepo = repos.teachers();
        this.staffRepo = repos.staffs();
    }

    // TODO: factor out code as it's similar to ListAvailableCoursesDTO
    public List<AvailableCourseDTO> courses() {
        var user = this.authz.session()
                .orElseThrow(() -> new IllegalStateException("User not logged in"))
                .authenticatedUser();

        var teacher = this.teacherRepo.findBySystemUser(user)
                .orElseThrow(() -> new IllegalStateException("User not registered as Teacher"));

        return new AvailableCourseDTOMapper().toDTO(this.staffRepo.nonClosedAndTaughtBy(teacher));
    }

    public boolean addExamQuestion(AvailableCourseDTO courseDTO, File file) throws IOException {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);

        var course = this.courseRepo.ofIdentity(courseDTO.courseId())
                .orElseThrow(() -> new ConcurrencyException("Course no longer exists"));

        Optional<Question> question = new QuestionFactory().build(course, file);

        question.ifPresent(this.repo::save);
        return question.isPresent();
    }
}
