package eapli.base.exam.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import eapli.base.course.domain.CourseID;
import eapli.base.exam.domain.regular_exam.RegularExamDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class OngoingExamDTO {
    private static final DateTimeFormatter fmt;

    static {
        fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    @Deprecated
    private Integer examId;
    private CourseID course;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public OngoingExamDTO(Integer examId, final CourseID name, final RegularExamDate date) {
        this.examId = examId;
        this.course = name;
        this.startTime = date.openDate();
        this.endTime = date.closeDate();
    }

    @Override
    public String toString() {
        return String.format("%s: From %s To %s", this.course, fmt.format(this.startTime),
                fmt.format(this.endTime));
    }
}
