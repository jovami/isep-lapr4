package jovami.grammar.impl.antlr;

import eapli.base.exam.dto.resolution.ExamResolutionDTO;
import eapli.base.examresult.dto.grade.ExamResultDTO;
import eapli.base.formativeexam.application.parser.GradeFormativeExamService;
import eapli.base.formativeexam.domain.FormativeExam;
import eapli.base.question.domain.Question;

import java.util.List;

public class ANTLRFormativeExamGrader implements GradeFormativeExamService {

    @Override
    public ExamResultDTO correctExam(FormativeExam exam, ExamResolutionDTO resolution, List<Question> questions) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
