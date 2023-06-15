package eapli.base.exam.application;

import java.util.List;

import eapli.framework.domain.repositories.IntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.exam.domain.RegularExamTitle;
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
import eapli.base.infrastructure.WebAuthService;
import eapli.base.infrastructure.grammar.GrammarContext;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;

/**
 * TakeExamController
 */
@UseCaseController
@RestController
@RequestMapping("api/examtaking/regular")
public final class TakeExamController {

    @Autowired
    private WebAuthService authz;
    private final ListOngoingExamsService svc;

    private final RegularExamRepository examRepo;
    private final RegularExamResultRepository resultRepo;

    public TakeExamController() {
        super();
        this.svc = new ListOngoingExamsService();

        var repos = PersistenceContext.repositories();
        this.examRepo = repos.regularExams();
        this.resultRepo = repos.examResults();
    }

    @GetMapping("/exam-list")
    public List<OngoingExamDTO> ongoingExams() {
        this.authz.ensureLoggedInWithRoles(BaseRoles.STUDENT);

        var exams = this.svc.forStudent(new MyUserService().currentStudent());

        return new OngoingExamDTOMapper().toDTO(exams);
    }

    @RequestMapping("/take")
    public ResponseEntity<ExamToBeTakenDTO> examToBeTaken(@RequestBody OngoingExamDTO examDto) {
        this.authz.ensureLoggedInWithRoles(BaseRoles.STUDENT);

        var exam = this.examRepo.ofIdentity(RegularExamTitle.valueOf(examDto.getTitle()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Could not find requested exam"));

        var dto = GrammarContext.grammarTools().examParserService()
                .generateExam(exam);

        return ResponseEntity.ok(dto);
    }

    @RequestMapping("/grade")
    public ResponseEntity<ExamResultDTO> examGrading(@RequestBody ExamResolutionDTO resolutionDTO) {
        this.authz.ensureLoggedInWithRoles(BaseRoles.STUDENT);

        var exam = this.examRepo.ofIdentity(RegularExamTitle.valueOf(resolutionDTO.getTitle()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Could not find requested exam"));

        if (exam.date().closeDate().isBefore(resolutionDTO.getSubmissionTime()))
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY);

        var correction = GrammarContext.grammarTools().examGrader()
                .correctExam(exam, resolutionDTO);

        var result = new RegularExamResult(new MyUserService().currentStudent(),
                exam, ExamGrade.valueOf(correction.grade(), correction.maxGrade()),
                correction.gradeProps());

        try {
            this.resultRepo.save(result);
        } catch (IntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Exam already graded");
        }

        return ResponseEntity.ok(new ExamResultDTOMapper().toDTO(correction));
    }
}
