package eapli.base.examresult.dto.grade;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * ExamResultDTO
 */
@Getter
@AllArgsConstructor
@ToString
public class ExamResultDTO {

    @Getter
    @AllArgsConstructor
    @ToString
    public static final class Section {
        private final List<Answer> answers;
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static final class Answer {
        private final Float points;
        private final Float maxPoints;
        private final String feedback;
    }

    private final List<Section> sections;
    private final Float grade;
    private final Float maxGrade;
}
