package eapli.base.exam.application.parser;

import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.dto.resolution.ExamResolutionDTO;
import eapli.base.examresult.dto.grade.ExamResultDTO;

/**
 * GradeExamService
 */
public interface GradeExamService {

    ExamResultDTO correctExam(RegularExam exam, ExamResolutionDTO resolution);
}
