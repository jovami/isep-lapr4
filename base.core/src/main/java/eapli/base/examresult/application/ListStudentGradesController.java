package eapli.base.examresult.application;

import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.examresult.dto.ExamGradeAndCourseDTO;
import eapli.base.examresult.dto.ExamGradeAndCourseDTOMapper;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.List;

import static eapli.base.clientusermanagement.usermanagement.domain.BaseRoles.STUDENT;

@UseCaseController
public class ListStudentGradesController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final StudentRepository studentRepo;
    private final ListExamResultsService svc;

    public ListStudentGradesController() {
        this.studentRepo = PersistenceContext.repositories().students();
        this.svc = new ListExamResultsService();
    }

    public List<ExamGradeAndCourseDTO> listStudentGrades() {
        if (!this.authz.isAuthenticatedUserAuthorizedTo(STUDENT)) {
            throw new IllegalStateException("User is not of an appropriate role");
        }

        var student = this.studentRepo.findBySystemUser(getUser())
                .orElseThrow(() -> new IllegalStateException("User not registered as Student"));

        return new ExamGradeAndCourseDTOMapper().toDTO(this.svc.regularExamResultsBasedOnGradingProperties(student));
    }

    private SystemUser getUser() throws IllegalStateException {
        return this.authz.session()
                .orElseThrow(() -> new IllegalStateException("User not logged in"))
                .authenticatedUser();
    }
}

