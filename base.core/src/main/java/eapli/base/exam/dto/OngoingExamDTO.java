package eapli.base.exam.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import eapli.base.course.domain.CourseID;
import eapli.base.exam.domain.RegularExamDate;
import eapli.base.exam.domain.RegularExamTitle;
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

    private String title;
    private String course;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public OngoingExamDTO(final RegularExamTitle title, final CourseID name, final RegularExamDate date) {
        this.title = title.title();
        this.course = name.id();
        this.startTime = date.openDate();
        this.endTime = date.closeDate();
    }

    @Override
    public String toString() {
        return String.format("%s: From %s To %s", this.course, fmt.format(this.startTime),
                fmt.format(this.endTime));
    }
}
