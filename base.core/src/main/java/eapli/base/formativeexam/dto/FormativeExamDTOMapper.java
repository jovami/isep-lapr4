package eapli.base.formativeexam.dto;

import eapli.base.formativeexam.domain.FormativeExam;
import jovami.util.dto.Mapper;

/**
 * FormativeExamDTOMapper
 */
public final class FormativeExamDTOMapper implements Mapper<FormativeExam, FormativeExamDTO> {

    @Override
    public FormativeExamDTO toDTO(FormativeExam exam) {
        return new FormativeExamDTO(exam.identity().title(), exam.course().identity().id());
    }

}
