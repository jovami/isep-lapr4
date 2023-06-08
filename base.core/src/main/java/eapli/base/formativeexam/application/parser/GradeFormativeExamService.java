package eapli.base.formativeexam.application.parser;

import eapli.base.examresult.dto.grade.ExamResultDTO;
import eapli.base.formativeexam.dto.resolution.FormativeExamResolutionDTO;
import eapli.base.question.domain.Question;

import java.util.Map;

public interface GradeFormativeExamService {
    ExamResultDTO correctExam(/* FormativeExam exam, */
            FormativeExamResolutionDTO resolution,
            Map<Long, Question> questions);
}
