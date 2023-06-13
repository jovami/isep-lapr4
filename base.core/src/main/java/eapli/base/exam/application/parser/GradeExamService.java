package eapli.base.exam.application.parser;

import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.dto.CorrectedExamDTO;
import eapli.base.exam.dto.resolution.ExamResolutionDTO;

/**
 * GradeExamService
 */
public interface GradeExamService {

    CorrectedExamDTO correctExam(RegularExam exam, ExamResolutionDTO resolution);
}
