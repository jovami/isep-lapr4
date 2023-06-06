package eapli.base.exam.dto;

import eapli.base.exam.domain.regular_exam.antlr.Section;

import java.util.List;

public class ExamToBeTakenDTO {
    private String title;
    private String description;
    private List<Section> sections;

    public ExamToBeTakenDTO(String title, String description, List<Section> sections) {
        this.title = title;
        this.description = description;
        this.sections = sections;
    }
}
