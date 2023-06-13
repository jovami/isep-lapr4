package eapli.base.formativeexam.dto.resolution;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * FormativeExamResolutionDTO
 */
/* for jackson */
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//======//
@Getter
@AllArgsConstructor
public final class FormativeExamResolutionDTO {

    /* for jackson */
    @Setter(value = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    //======//
    @Getter
    @AllArgsConstructor
    public static final class SectionAnswers {
        private List<GivenAnswer> answers;
    }

    /* for jackson */
    @Setter(value = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    //======//
    @Getter
    @AllArgsConstructor
    public static final class GivenAnswer {
        private String answer;
        private Long questionID;
    }

    private List<SectionAnswers> sectionAnswers;
}
