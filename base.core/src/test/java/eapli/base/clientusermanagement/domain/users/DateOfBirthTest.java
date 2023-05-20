package eapli.base.clientusermanagement.domain.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class DateOfBirthTest {

    @Test
    public void dateOfBirthCreationValidDateOfBirthSuccess() {
        LocalDate validDateOfBirth = LocalDate.of(1990, 5, 19);
        String validDateOfBirthString = validDateOfBirth.format(DateTimeFormatter.ISO_LOCAL_DATE);

        DateOfBirth dateOfBirth = DateOfBirth.valueOf(validDateOfBirthString);

        assertEquals(validDateOfBirthString, dateOfBirth.toString());
    }

    @Test
    public void dateOfBirthCreationEmptyDateOfBirthStringThrowsException() {
        String emptyDateOfBirthString = "";

        assertThrows(IllegalArgumentException.class, () -> DateOfBirth.valueOf(emptyDateOfBirthString));
    }

    @Test
    public void dateOfBirthCreationInvalidDateOfBirthStringThrowsException() {
        String invalidDateOfBirthString = "4000-05-19"; // Future date

        assertThrows(IllegalArgumentException.class, () -> DateOfBirth.valueOf(invalidDateOfBirthString));
    }

    @Test
    public void dateOfBirthComparisonSameDateOfBirthReturnsZero() {
        LocalDate sameDate = LocalDate.of(1990, 5, 19);
        DateOfBirth dateOfBirth1 = new DateOfBirth(sameDate);
        DateOfBirth dateOfBirth2 = new DateOfBirth(sameDate);

        int result = dateOfBirth1.compareTo(dateOfBirth2);

        assertEquals(0, result);
    }

    @Test
    public void dateOfBirthComparisonDifferentDateOfBirthReturnsNonZero() {
        LocalDate date1 = LocalDate.of(1990, 5, 19);
        LocalDate date2 = LocalDate.of(1995, 10, 10);
        DateOfBirth dateOfBirth1 = new DateOfBirth(date1);
        DateOfBirth dateOfBirth2 = new DateOfBirth(date2);

        int result = dateOfBirth1.compareTo(dateOfBirth2);

        assertNotEquals(0, result);
    }

    @Test
    public void dateOfBirthToStringValidDateOfBirthReturnsFormattedString() {
        LocalDate validDateOfBirth = LocalDate.of(1990, 5, 19);
        DateOfBirth dateOfBirth = new DateOfBirth(validDateOfBirth);
        String expectedString = "1990-05-19";

        String result = dateOfBirth.toString();

        assertEquals(expectedString, result);
    }
}
