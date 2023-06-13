package eapli.base.exam.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

@Embeddable
public class RegularExamDate implements ValueObject {

    private static final long serialVersionUID = 1L;

    private LocalDateTime start;
    private LocalDateTime end;

    protected RegularExamDate() {
        this.start = null;
        this.end = null;
    }

    public RegularExamDate(LocalDateTime start, LocalDateTime end) {
        Preconditions.noneNull(start, end);
        Preconditions.ensure(start.isBefore(end), "The open date must be before the close date");

        this.start = start;
        this.end = end;
    }

    public static RegularExamDate valueOf(LocalDateTime openDate, LocalDateTime closeDate) {
        return new RegularExamDate(openDate, closeDate);
    }

    public LocalDateTime openDate() {
        return this.start;
    }

    public LocalDateTime closeDate() {
        return this.end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RegularExamDate that = (RegularExamDate) o;
        return this.start.equals(that.start) && this.end.equals(that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d/M/yyyy H:m");
        String open = fmt.format(start);
        String close = fmt.format(end);
        return "Opening: " + open +
                " | Closing: " + close;
    }
}
