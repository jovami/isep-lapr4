package eapli.base.formativeexam.dto.resolution;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.question.domain.Question;
import eapli.base.question.repositories.QuestionRepository;

/**
 * FormativeExamResolutionDTOUnmapper
 */
public final class FormativeExamResolutionDTOUnmapper {
    private final QuestionRepository repo;

    public FormativeExamResolutionDTOUnmapper() {
        super();
        this.repo = PersistenceContext.repositories().questions();
    }

    public Map<Long, Question> fromDTO(FormativeExamResolutionDTO resolutionDTO) {
        return resolutionDTO.getSectionAnswers().stream()
                .flatMap(sec -> sec.getAnswers().stream())
                .map(answer -> this.repo.ofIdentity(answer.getQuestionID())
                        .orElseThrow(IllegalStateException::new))
                .collect(Collectors.toMap(Question::identity, Function.identity()));
    }
}
