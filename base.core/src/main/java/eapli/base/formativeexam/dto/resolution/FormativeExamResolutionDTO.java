package eapli.base.formativeexam.dto.resolution;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * FormativeExamResolutionDTO
 */
@Accessors(fluent = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class FormativeExamResolutionDTO {

    @Accessors(fluent = true)
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class Section {
        private List<GivenAnswer> answers;
    }

    @Accessors(fluent = true)
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class GivenAnswer {
        private String answer;
        private Long questionID;
    }

    private List<Section> sections;

}
