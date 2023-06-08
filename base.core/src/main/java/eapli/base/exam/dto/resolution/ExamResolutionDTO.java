package eapli.base.exam.dto.resolution;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * ExamResolutionDTO
 */
@Accessors(fluent = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class ExamResolutionDTO {

    @Accessors(fluent = true)
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class Section {

        private List<String> answers;
    }

    private List<Section> sections;

}
