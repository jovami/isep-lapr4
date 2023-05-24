package eapli.base.course.domain;

import java.time.LocalDate;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

@Embeddable
public class CourseDuration implements ValueObject {
    private final LocalDate start;
    private final LocalDate end;

    protected CourseDuration() {
        this.start = null;
        this.end = null;
    }

    protected CourseDuration(LocalDate start, LocalDate end) {
        Preconditions.noneNull(start, end);
        Preconditions.ensure(start.isBefore(end), "Start date must be before end date");

        this.start = start;
        this.end = end;
    }

    public static CourseDuration valueOf(LocalDate start, LocalDate end){
        return new CourseDuration(start, end);
    }

    public LocalDate startDate() {
        return this.start;
    }

    public LocalDate endDate() {
        return this.end;
    }

    @Override
    public String toString() {
        return start +  " - " + end;
    }
}
