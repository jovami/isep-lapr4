package eapli.base.exam.dto;

import eapli.base.exam.domain.RegularExam;
import jovami.util.dto.Mapper;

/**
 * FutureExamDTOMapper
 */
public final class OngoingExamDTOMapper implements Mapper<RegularExam, OngoingExamDTO> {

    @Override
    public OngoingExamDTO toDTO(final RegularExam exam) {
        return new OngoingExamDTO(exam.identity(), exam.course().identity(), exam.date());
    }
}
