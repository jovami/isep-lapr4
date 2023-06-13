package eapli.base.exam.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import eapli.base.course.domain.CourseID;
import eapli.base.exam.domain.RegularExamDate;

/**
 * FutureExamDTO
 */
public final class FutureExamDTO {
    private static final DateTimeFormatter fmt;

    static {
        fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    private final CourseID course;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public FutureExamDTO(final CourseID name, final RegularExamDate date) {
        this.course = name;
        this.startTime = date.openDate();
        this.endTime = date.closeDate();
    }

    public CourseID id() {
        return this.course;
    }

    public LocalDateTime startTime() {
        return this.startTime;
    }

    public LocalDateTime endTime() {
        return this.endTime;
    }

    @Override
    public String toString() {
        return String.format("%s: From %s To %s", this.course, fmt.format(this.startTime),
                fmt.format(this.endTime));
    }
}
