package eapli.base.formativeexam.application;

import java.util.List;

import eapli.base.formativeexam.domain.FormativeExamTitle;
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
import eapli.base.exam.dto.ExamToBeTakenDTO;
import eapli.base.examresult.dto.grade.ExamResultDTO;
import eapli.base.formativeexam.dto.FormativeExamDTO;
import eapli.base.formativeexam.dto.FormativeExamDTOMapper;
import eapli.base.formativeexam.dto.resolution.FormativeExamResolutionDTO;
import eapli.base.formativeexam.dto.resolution.FormativeExamResolutionDTOUnmapper;
import eapli.base.formativeexam.repositories.FormativeExamRepository;
import eapli.base.infrastructure.WebAuthService;
import eapli.base.infrastructure.grammar.GrammarContext;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.question.repositories.QuestionRepository;
import eapli.framework.application.UseCaseController;
import jovami.util.grammar.NotEnoughQuestionsException;

/**
 * TakeFormativeExamController
 */
@UseCaseController
@RestController
@RequestMapping("api/examtaking/formative")
public final class TakeFormativeExamController {

    @Autowired
    private WebAuthService authz;

    private final FormativeExamRepository fexamRepo;
    private final QuestionRepository questionRepo;

    public TakeFormativeExamController() {
        super();

        var repos = PersistenceContext.repositories();
        this.fexamRepo = repos.formativeExams();
        this.questionRepo = repos.questions();
    }

    @GetMapping("/exam-list")
    public List<FormativeExamDTO> formativeExams() {
        this.authz.ensureLoggedInWithRoles(BaseRoles.STUDENT);

        var exams = new ListFormativeExamsService().forStudent(
                new MyUserService().currentStudent());
        return new FormativeExamDTOMapper().toDTO(exams);
    }

    @RequestMapping("/take")
    public ResponseEntity<ExamToBeTakenDTO> generateFormativeExam(@RequestBody FormativeExamDTO examDTO) {
        this.authz.ensureLoggedInWithRoles(BaseRoles.STUDENT);

        var exam = this.fexamRepo.ofIdentity(FormativeExamTitle.valueOf(examDTO.getExamName()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Could not find requested formative exam"));

        try {
            var questions = this.questionRepo.questionsOfCourse(exam.course());

            var dto = GrammarContext.grammarTools().formativeExamGenerator()
                    .generate(exam, questions);

            return ResponseEntity.ok(dto);
        } catch (NotEnoughQuestionsException e) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, e.getMessage());
        }
    }

    @RequestMapping("/grade")
    public ResponseEntity<ExamResultDTO> examGrading(@RequestBody FormativeExamResolutionDTO resolutionDTO) {
        this.authz.ensureLoggedInWithRoles(BaseRoles.STUDENT);

        var questions = new FormativeExamResolutionDTOUnmapper().fromDTO(resolutionDTO);
        var dto = GrammarContext.grammarTools().formativeExamGrader()
                .correctExam(resolutionDTO, questions);

        return ResponseEntity.ok(dto);
    }
}
