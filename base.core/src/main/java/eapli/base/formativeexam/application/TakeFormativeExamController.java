package eapli.base.formativeexam.application;

import java.util.List;

import eapli.base.exam.dto.resolution.ExamResolutionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("api/examtaking")
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

    @GetMapping("/formative-exams")
    public List<FormativeExamDTO> formativeExams() {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);

        var exams = new ListFormativeExamsService().forStudent(
                new MyUserService().currentStudent());
        return new FormativeExamDTOMapper().toDTO(exams);
    }

    @RequestMapping("/take")
    public ResponseEntity<ExamToBeTakenDTO> generateFormativeExam(@RequestBody FormativeExamDTO examDTO) {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);

        var exam = this.fexamRepo.ofIdentity(examDTO.getExamId())
                .orElseThrow(IllegalStateException::new);
        var questions = this.questionRepo.questionsOfCourse(exam.course());
        var dto = GrammarContext.grammarTools().formativeExamGenerator().generate(exam, questions);

        return ResponseEntity.ok(dto);
    }

    @RequestMapping("/grade")
    public ResponseEntity<ExamResultDTO> examGrading(FormativeExamResolutionDTO resolutionDTO) {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);

        var questions = new FormativeExamResolutionDTOUnmapper().fromDTO(resolutionDTO);
        var dto = GrammarContext.grammarTools().formativeExamGrader().correctExam(resolutionDTO, questions);

        return ResponseEntity.ok(dto);
    }
}