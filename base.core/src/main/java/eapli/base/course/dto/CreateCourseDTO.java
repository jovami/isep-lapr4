package eapli.base.course.dto;

import java.time.LocalDate;

/**
 * CreateCourseDTO
 */
public class CreateCourseDTO {
    private final String title;
    private final Long code;
    private final String description;

    private final LocalDate startDate;
    private final LocalDate endDate;

    private final int minCapacity;
    private final int maxCapacity;

    public CreateCourseDTO(String title, Long code, String description,
            LocalDate startDate, LocalDate endDate,
            int minCapacity, int maxCapacity) {
        this.title = title;
        this.code = code;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minCapacity = minCapacity;
        this.maxCapacity = maxCapacity;
    }

    public String title() {
        return this.title;
    }

    public Long code() {
        return this.code;
    }

    public String description() {
        return this.description;
    }

    public int minCapacity() {
        return this.minCapacity;
    }

    public int maxCapacity() {
        return this.maxCapacity;
    }

    public LocalDate startDate() {
        return this.startDate;
    }

    public LocalDate endDate() {
        return this.endDate;
    }

    @Override
    public String toString() {
        return "Title: " + this.title
                + "\nCode: " + this.code
                + "\nDuration: from " + this.startDate + " to " + this.endDate
                + "\nCapacity: " + this.minCapacity + " - " + this.maxCapacity;
    }
}
