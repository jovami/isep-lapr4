package eapli.base.formativeexam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * FormativeExamDTO
 */
@Accessors(fluent = true)
@Getter
@AllArgsConstructor
public class FormativeExamDTO {

    @Deprecated
    private final Long examId;

    private final String examName = "";
    private final String courseName;

    @Override
    public String toString() {
        return String.format("%s --- %s", this.courseName, this.examName);
    }
}
