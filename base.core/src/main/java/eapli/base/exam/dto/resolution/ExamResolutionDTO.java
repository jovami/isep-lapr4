package eapli.base.exam.dto.resolution;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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

}
