package eapli.base.exam.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Embeddable
public class RegularExamDate implements ValueObject {

    private static final long serialVersionUID = 1L;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    protected RegularExamDate() {
        this.startDate = null;
        this.endDate = null;
    }

    public RegularExamDate(LocalDateTime startDate, LocalDateTime endDate) {
        Preconditions.noneNull(startDate, endDate);
        Preconditions.ensure(startDate.isBefore(endDate), "The open date must be before the close date");

        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static RegularExamDate valueOf(LocalDateTime openDate, LocalDateTime closeDate) {
        return new RegularExamDate(openDate, closeDate);
    }

    public LocalDateTime openDate() {
        return this.startDate;
    }

    public LocalDateTime closeDate() {
        return this.endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RegularExamDate that = (RegularExamDate) o;
        return this.startDate.equals(that.startDate) && this.endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d/M/yyyy H:m");
        String open = fmt.format(startDate);
        String close = fmt.format(endDate);
        return "Opening: " + open +
                " | Closing: " + close;
    }
}
