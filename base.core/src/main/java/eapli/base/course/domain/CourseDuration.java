package eapli.base.course.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class CourseDuration implements ValueObject {
    private final LocalDate startDate;
    private final LocalDate endDate;

    protected CourseDuration() {
        this.startDate = null;
        this.endDate = null;
    }

    protected CourseDuration(LocalDate startDate, LocalDate endDate) {
        Preconditions.noneNull(startDate, endDate);
        Preconditions.ensure(startDate.isBefore(endDate), "Start date must be before end date");

        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static CourseDuration valueOf(LocalDate start, LocalDate end){
        return new CourseDuration(start, end);
    }

    public LocalDate startDate() {
        return this.startDate;
    }

    public LocalDate endDate() {
        return this.endDate;
    }

    @Override
    public String toString() {
        return startDate +  " - " + endDate;
    }
}
