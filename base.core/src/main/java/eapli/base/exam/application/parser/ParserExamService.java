package eapli.base.exam.application.parser;

import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.dto.ExamToBeTakenDTO;

public interface ParserExamService {
    ExamToBeTakenDTO generateExam(RegularExam exam);
}
