package eapli.base.formativeexam.dto.resolution;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * FormativeExamResolutionDTO
 */
/* for jackson */
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//======//
@Accessors(fluent = true)
@Getter
@AllArgsConstructor
public final class FormativeExamResolutionDTO {

    /* for jackson */
    @Setter(value = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    //======//
    @Accessors(fluent = true)
    @Getter
    @AllArgsConstructor
    public static final class Section {
        private List<GivenAnswer> answers;
    }

    /* for jackson */
    @Setter(value = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    //======//
    @Accessors(fluent = true)
    @Getter
    @AllArgsConstructor
    public static final class GivenAnswer {
        private String answer;
        private Long questionID;
    }

    private List<Section> sections;

}
