package eapli.base.examresult.application;

import static eapli.base.clientusermanagement.usermanagement.domain.BaseRoles.STUDENT;

import java.util.List;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.examresult.dto.ExamGradeAndCourseDTO;
import eapli.base.examresult.dto.ExamGradeAndCourseDTOMapper;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public class ListStudentGradesController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ListExamResultsService svc;

    public ListStudentGradesController() {
        this.svc = new ListExamResultsService();
    }

    public List<ExamGradeAndCourseDTO> listStudentGrades() {
        if (!this.authz.isAuthenticatedUserAuthorizedTo(STUDENT)) {
            throw new IllegalStateException("User is not of an appropriate role");
        }

        var student = new MyUserService().currentStudent();

        return new ExamGradeAndCourseDTOMapper().toDTO(this.svc.regularExamResultsBasedOnGradingProperties(student));
    }
}
