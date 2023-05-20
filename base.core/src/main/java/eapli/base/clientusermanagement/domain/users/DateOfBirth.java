package eapli.base.clientusermanagement.domain.users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

@Embeddable
public class DateOfBirth implements ValueObject, Comparable<DateOfBirth> {
    private static final long serialVersionUID = 1L;
    private final LocalDate dateOfBirth;

    public DateOfBirth(LocalDate dateOfBirth) {
        Preconditions.nonNull(dateOfBirth, "Date of birth should not be null");
        Preconditions.ensure(isDateOfBirthValid(dateOfBirth), "Invalid date of birth");

        this.dateOfBirth = dateOfBirth;
    }

    // for ORM
    protected DateOfBirth() {
        dateOfBirth = null;
    }

    public static DateOfBirth valueOf(final String dateOfBirthString) {
        Preconditions.nonEmpty(dateOfBirthString, "Date of birth should not be empty");
        Preconditions.nonNull(dateOfBirthString, "Date of birth should not be null");

        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, DateTimeFormatter.ISO_LOCAL_DATE);
        Preconditions.ensure(isDateOfBirthValid(dateOfBirth), "Invalid date of birth");

        return new DateOfBirth(dateOfBirth);
    }

    /**
     * Checks if a given date of birth is valid.
     *
     * @param dateOfBirth the date of birth to be validated
     * @return true if the date of birth is on or before the current date, false
     *         otherwise
     */
    private static boolean isDateOfBirthValid(LocalDate dateOfBirth) {
        return !dateOfBirth.isAfter(LocalDate.now());
    }

    @Override
    public String toString() {
        return dateOfBirth.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public int compareTo(final DateOfBirth o) {
        return dateOfBirth.compareTo(o.dateOfBirth);
    }
}
