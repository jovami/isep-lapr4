package eapli.base.examresult.dto;

import eapli.base.examresult.domain.RegularExamResult;
import jovami.util.dto.Mapper;

public class ExamGradeAndStudentDTOMapper implements Mapper<RegularExamResult, ExamGradeAndStudentDTO> {
    @Override
    public ExamGradeAndStudentDTO toDTO(RegularExamResult result) {
        return new ExamGradeAndStudentDTO(result.grade(), result.regularExam().regularExamDate(), result.student());
    }
}
