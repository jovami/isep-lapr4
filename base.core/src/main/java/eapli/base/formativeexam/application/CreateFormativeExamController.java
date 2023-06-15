package eapli.base.formativeexam.application;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.dto.AvailableCourseDTO;
import eapli.base.course.dto.AvailableCourseDTOMapper;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.formativeexam.domain.FormativeExam;
import eapli.base.formativeexam.domain.FormativeExamFactory;
import eapli.base.formativeexam.repositories.FormativeExamRepository;
import eapli.base.infrastructure.grammar.GrammarContext;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 * CreateFormativeExamController
 */
@UseCaseController
public class CreateFormativeExamController {
    private final AuthorizationService authz;
    private final FormativeExamRepository repo;

    private final CourseRepository courseRepo;
    private final StaffRepository staffRepo;

    public CreateFormativeExamController() {
        var repos = PersistenceContext.repositories();
        this.authz = AuthzRegistry.authorizationService();

        this.repo = repos.formativeExams();

        this.courseRepo = repos.courses();
        this.staffRepo = repos.staffs();
    }

    public List<AvailableCourseDTO> courses() {
        var teacher = new MyUserService().currentTeacher();
        return new AvailableCourseDTOMapper().toDTO(this.staffRepo.nonClosedAndTaughtBy(teacher));
    }

    public boolean createFormativeExam(String title, AvailableCourseDTO courseDTO, File file) throws IOException {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);

        var course = this.courseRepo.ofIdentity(courseDTO.courseId())
                .orElseThrow(() -> new ConcurrencyException("Course no longer exists"));

        var validator = GrammarContext.grammarTools().formativeExamValidator();
        Optional<FormativeExam> fexam = new FormativeExamFactory(validator).build(title, course, file);

        fexam.ifPresent(this.repo::save);
        return fexam.isPresent();
    }
}
