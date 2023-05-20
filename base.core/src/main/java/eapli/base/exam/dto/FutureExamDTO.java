package eapli.base.exam.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import eapli.base.course.domain.CourseName;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamDate;

/**
 * FutureExamDTO
 */
public final class FutureExamDTO {

    private final CourseName courseName;
    // TODO: change back to LocalDateTime when ExamDate gets fixed
    private final LocalDate startTime;
    private final LocalDate endTime;
    // private final LocalDateTime startTime;
    // private final LocalDateTime endTime;

    private static final DateTimeFormatter fmt;

    static {
        // fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    }

    public FutureExamDTO(final CourseName name, final RegularExamDate date) {
        this.courseName = CourseName.valueOf(name.getName());
        this.startTime = toLocalDateTime(date.openDate());
        this.endTime = toLocalDateTime(date.closeDate());
    }

    private static LocalDate toLocalDateTime(final Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // private static LocalDateTime toLocalDateTime(final Date date) {
    // return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    // }

    public CourseName name() {
        return this.courseName;
    }

    // public LocalDateTime startTime() {
    // return this.startTime;
    // }

    // public LocalDateTime endTime() {
    // return this.endTime;
    // }

    @Override
    public String toString() {
        return String.format("%s: From %s To %s", this.courseName, fmt.format(this.startTime),
                fmt.format(this.endTime));
    }
}
