package eapli.base.formativeexam.application.parser;

import eapli.base.exam.dto.resolution.ExamResolutionDTO;
import eapli.base.examresult.dto.grade.ExamResultDTO;
import eapli.base.formativeexam.domain.FormativeExam;
import eapli.base.question.domain.Question;

import java.util.List;

public interface GradeFormativeExamService {
    ExamResultDTO correctExam(FormativeExam exam, ExamResolutionDTO resolution, List<Question> questions);
}
