package eapli.base.exam.dto.resolution;

import java.time.LocalDateTime;
import java.util.List;

import eapli.base.exam.domain.RegularExamTitle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ExamResolutionDTO
 */
/* for jackson */
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//======//
@Getter
@AllArgsConstructor
public final class ExamResolutionDTO {

    /* for jackson */
    @Setter(value = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    //======//
    @Getter
    @AllArgsConstructor
    public static final class Section {
        private List<String> answers;
    }

    private List<Section> sections;

    private LocalDateTime submissionTime;

    private String title;

}
