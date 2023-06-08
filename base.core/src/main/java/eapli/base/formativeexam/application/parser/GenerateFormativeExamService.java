package eapli.base.formativeexam.application.parser;

import eapli.base.exam.dto.ExamToBeTakenDTO;
import eapli.base.formativeexam.domain.FormativeExam;
import eapli.base.question.domain.Question;

public interface GenerateFormativeExamService {
    ExamToBeTakenDTO generate(FormativeExam exam, Iterable<Question> questions);
}
