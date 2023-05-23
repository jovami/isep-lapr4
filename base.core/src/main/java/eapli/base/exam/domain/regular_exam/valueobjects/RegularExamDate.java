package eapli.base.exam.domain.regular_exam.valueobjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String open = dateFormat.format(start);
        String close = dateFormat.format(end);
        return "Opening: " + open +
                " | Closing: " + close;
    }
}
