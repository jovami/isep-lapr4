package eapli.base.formativeexam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * FormativeExamDTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormativeExamDTO {

    private String examName;
    private String courseName;

    @Override
    public String toString() {
        return String.format("%s --- %s", this.courseName, this.examName);
    }
}
