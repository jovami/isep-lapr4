package eapli.base.exam.application;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.exam.dto.ExamToBeTakenDTO;
import eapli.base.exam.dto.OngoingExamDTO;
import eapli.base.exam.dto.OngoingExamDTOMapper;
import eapli.base.exam.dto.resolution.ExamResolutionDTO;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.examresult.domain.ExamGrade;
import eapli.base.examresult.domain.RegularExamResult;
import eapli.base.examresult.dto.grade.ExamResultDTO;
import eapli.base.examresult.dto.grade.ExamResultDTOMapper;
import eapli.base.examresult.repository.RegularExamResultRepository;
import eapli.base.infrastructure.grammar.GrammarContext;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 * TakeExamController
 */
@UseCaseController
@RestController
@RequestMapping("api/examtaking/regular")
public final class TakeExamController {

    private final AuthorizationService authz;
    private final ListOngoingExamsService svc;

    private final RegularExamRepository examRepo;
    private final RegularExamResultRepository resultRepo;

    public TakeExamController() {
        super();
        this.authz = AuthzRegistry.authorizationService();
        this.svc = new ListOngoingExamsService();

        var repos = PersistenceContext.repositories();
        this.examRepo = repos.regularExams();
        this.resultRepo = repos.examResults();
    }

    @GetMapping("/exam-list")
    public List<OngoingExamDTO> ongoingExams() {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);

        var exams = this.svc.forStudent(new MyUserService().currentStudent());

        return new OngoingExamDTOMapper().toDTO(exams);
    }

    @RequestMapping("/take")
    public ResponseEntity<ExamToBeTakenDTO> examToBeTaken(@RequestBody OngoingExamDTO examDto) {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);

        var exam = this.examRepo.ofIdentity(examDto.getExamId());

        if (exam.isEmpty()) {
            // TODO: confirm
            return ResponseEntity.notFound().build();
        }

        var dto = GrammarContext.grammarTools().examParserService()
                .generateExam(exam.get());

        return ResponseEntity.ok(dto);
    }

    @RequestMapping("/grade")
    public ResponseEntity<ExamResultDTO> examGrading(@RequestBody ExamResolutionDTO resolutionDTO) {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);

        var examOpt = this.examRepo.ofIdentity(resolutionDTO.getExamID());

        if (examOpt.isEmpty()) {
            // TODO: confirm
            return ResponseEntity.notFound().build();
        }

        var exam = examOpt.get();

        if (exam.regularExamDate().closeDate().isBefore(resolutionDTO.getSubmissionTime())) {
            // TODO: confirm
            return ResponseEntity.notFound().build();
        }

        var correction = GrammarContext.grammarTools().examGrader()
                .correctExam(exam, resolutionDTO);

        var result = new RegularExamResult(new MyUserService().currentStudent(), exam,
                ExamGrade.valueOf(correction.grade(), correction.maxGrade()), correction.gradeProps());
        this.resultRepo.save(result);

        return ResponseEntity.ok(new ExamResultDTOMapper().toDTO(correction));
    }
}
