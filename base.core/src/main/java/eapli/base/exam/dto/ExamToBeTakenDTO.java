package eapli.base.exam.dto;

import java.util.List;

import eapli.base.question.dto.AbstractQuestionDTO;
// import eapli.base.exam.domain.regular_exam.antlr.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
// @Setter
@AllArgsConstructor
// @NoArgsConstructor
public class ExamToBeTakenDTO {
    private String title;
    private String description;
    private List<Section> sections;

    @Getter
    @AllArgsConstructor
    public static final class Section {
        private int id;
        private final String description;
        private final List<AbstractQuestionDTO> questions;
    }
}
