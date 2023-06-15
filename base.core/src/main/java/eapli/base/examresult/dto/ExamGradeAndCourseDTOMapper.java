package eapli.base.examresult.dto;

import eapli.base.examresult.domain.RegularExamResult;
import jovami.util.dto.Mapper;

public class ExamGradeAndCourseDTOMapper implements Mapper<RegularExamResult, ExamGradeAndCourseDTO> {
    @Override
    public ExamGradeAndCourseDTO toDTO(RegularExamResult result) {
        return new ExamGradeAndCourseDTO(result.regularExam().identity(),
                result.grade(), result.regularExam().date(), result.regularExam().course().identity());
    }
}
