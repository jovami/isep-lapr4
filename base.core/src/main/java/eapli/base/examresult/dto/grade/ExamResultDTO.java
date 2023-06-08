package eapli.base.examresult.dto.grade;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * ExamResultDTO
 */
@Accessors(fluent = true)
@Getter
@AllArgsConstructor
@ToString
public class ExamResultDTO {

    @Accessors(fluent = true)
    @Getter
    @AllArgsConstructor
    @ToString
    public static final class Section {
        private final List<Answer> answers;
    }

    @Accessors(fluent = true)
    @Getter
    @AllArgsConstructor
    @ToString
    public static final class Answer {
        private final float points;
        private final String feedback;
    }

    private final List<Section> sections;
    private final Float grade;
    private final Float maxGrade;
}
