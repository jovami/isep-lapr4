package jovami.util.grammar;

import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.dto.ExamToBeTakenDTO;

import java.io.IOException;

public interface ExamParser {
    ExamToBeTakenDTO generateExam(RegularExam exam) throws IOException;
}
