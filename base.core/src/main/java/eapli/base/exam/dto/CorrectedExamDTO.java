package eapli.base.exam.dto;

import java.util.List;

import eapli.base.examresult.domain.ExamFeedbackProperties;
import eapli.base.examresult.domain.ExamGradeProperties;
import eapli.base.examresult.dto.grade.ExamResultDTO.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * CorrectedExamDTO
 */
@Accessors(fluent = true)
@Getter
@AllArgsConstructor
public class CorrectedExamDTO {

    private List<Section> sections;
    private final Float grade;
    private final Float maxGrade;

    private final ExamGradeProperties gradeProps;
    private final ExamFeedbackProperties feedbackProps;

}
