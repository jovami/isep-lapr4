package eapli.base.formativeexam.application;

import java.util.List;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.exam.dto.ExamToBeTakenDTO;
import eapli.base.examresult.dto.grade.ExamResultDTO;
import eapli.base.formativeexam.dto.FormativeExamDTO;
import eapli.base.formativeexam.dto.FormativeExamDTOMapper;
import eapli.base.formativeexam.dto.resolution.FormativeExamResolutionDTO;
import eapli.base.formativeexam.dto.resolution.FormativeExamResolutionDTOUnmapper;
import eapli.base.formativeexam.repositories.FormativeExamRepository;
import eapli.base.infrastructure.grammar.GrammarContext;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.question.repositories.QuestionRepository;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 * TakeFormativeExamController
 */
@UseCaseController
public final class TakeFormativeExamController {
    private final AuthorizationService authz;

    private final FormativeExamRepository fexamRepo;
    private final QuestionRepository questionRepo;

    public TakeFormativeExamController() {
        super();
        this.authz = AuthzRegistry.authorizationService();

        var repos = PersistenceContext.repositories();
        this.fexamRepo = repos.formativeExams();
        this.questionRepo = repos.questions();
    }

    public List<FormativeExamDTO> formativeExams() {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);

        var exams = new ListFormativeExamsService().forStudent(new MyUserService().currentStudent());
        return new FormativeExamDTOMapper().toDTO(exams);
    }

    public ExamToBeTakenDTO generateFormativeExam(Object/* TODO */examDTO) {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);

        var exam = this.fexamRepo.ofIdentity(null/* TODO */).orElseThrow(IllegalStateException::new);
        var questions = this.questionRepo.questionsOfCourse(exam.course());
        return GrammarContext.grammarTools().formativeExamGenerator().generate(exam, questions);
    }

    public ExamResultDTO examGrading(FormativeExamResolutionDTO resolutionDTO) {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);

        var questions = new FormativeExamResolutionDTOUnmapper().fromDTO(resolutionDTO);
        return GrammarContext.grammarTools().formativeExamGrader().correctExam(resolutionDTO, questions);
    }
}
