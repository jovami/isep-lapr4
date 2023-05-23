package eapli.base.exam.dto;

import eapli.base.course.domain.CourseName;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * FutureExamDTO
 */
public final class FutureExamDTO {
    private static final DateTimeFormatter fmt;

    static {
        fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    private final CourseName courseName;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public FutureExamDTO(final CourseName name, final RegularExamDate date) {
        this.courseName = name;
        this.startTime = date.openDate();
        this.endTime = date.closeDate();
    }

    public CourseName name() {
        return this.courseName;
    }

    public LocalDateTime startTime() {
        return this.startTime;
    }

    public LocalDateTime endTime() {
        return this.endTime;
    }

    @Override
    public String toString() {
        return String.format("%s: From %s To %s", this.courseName, fmt.format(this.startTime),
                fmt.format(this.endTime));
    }
}
