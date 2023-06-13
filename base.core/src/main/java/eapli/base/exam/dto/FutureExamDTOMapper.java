package eapli.base.exam.dto;

import eapli.base.exam.domain.RegularExam;
import jovami.util.dto.Mapper;

/**
 * FutureExamDTOMapper
 */
public final class FutureExamDTOMapper implements Mapper<RegularExam, FutureExamDTO> {

    @Override
    public FutureExamDTO toDTO(final RegularExam exam) {
        return new FutureExamDTO(exam.course().identity(), exam.date());
    }
}
