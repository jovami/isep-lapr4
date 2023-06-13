package eapli.base.exam.domain.regular_exam;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import eapli.base.exam.domain.RegularExamDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertThrows;

public class RegularExamDateTest {

    private RegularExamDate regularExamDate;

    @Before
    public void BeforeEach() {
        String openDateString = "10/10/2023 16:00";
        String closeDateString = "10/10/2023 18:00";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

        var openDate = LocalDateTime.parse(openDateString, df);
        var closeDate = LocalDateTime.parse(closeDateString, df);
        regularExamDate = RegularExamDate.valueOf(openDate, closeDate);
    }

    @Test
    public void valueOf() {
        String startDateString = "10/10/2023 16:00";
        String endDateString = "10/10/2023 18:00";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

        var startDate = LocalDateTime.parse(startDateString, df);
        var endDate = LocalDateTime.parse(endDateString, df);
        RegularExamDate createdDate = RegularExamDate.valueOf(startDate, endDate);
        Assertions.assertEquals(startDate, createdDate.openDate());
        Assertions.assertEquals(endDate, createdDate.closeDate());
    }

    @Test
    public void setIntervalDate_InvalidDates_ReturnsFalse() {
        String startDateString = "10/10/2023 16:00";
        String endDateString = "10/10/2023 14:00";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

        var startDate = LocalDateTime.parse(startDateString, df);
        var endDate = LocalDateTime.parse(endDateString, df);
        //RegularExamDate createdDate = RegularExamDate.valueOf(startDate, endDate);
        assertThrows(IllegalArgumentException.class, () -> {
            RegularExamDate.valueOf(startDate, endDate);
        });
    }

    @Test
    public void testEquals_SameDates_ReturnsTrue() {
        String startDateString = "10/10/2023 16:00";
        String endDateString = "10/10/2023 18:00";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

        var startDate = LocalDateTime.parse(startDateString, df);
        var endDate = LocalDateTime.parse(endDateString, df);
        RegularExamDate date1 = new RegularExamDate(startDate, endDate);
        RegularExamDate date2 = new RegularExamDate(startDate, endDate);
        Assertions.assertEquals(date1, date2);
    }

    @Test
    public void openDate_ReturnsCorrectValue() {
        String expectedDateString = "10/10/2023 16:00";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

        var expectedDate = LocalDateTime.parse(expectedDateString, df);
        var openDate = regularExamDate.openDate();
        Assertions.assertEquals(expectedDate, openDate);
    }

    @Test
    public void closeDate_ReturnsCorrectValue() {
        String expectedDateString = "10/10/2023 18:00";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

        var expectedDate = LocalDateTime.parse(expectedDateString, df);
        var closeDate = regularExamDate.closeDate();
        Assertions.assertEquals(expectedDate, closeDate);
    }

    @Test
    public void testEquals_DifferentDates_ReturnsFalse() {
        String startDateString1 = "10/10/2023 16:00";
        String endDateString1 = "10/10/2023 18:00";
        String startDateString2 = "10/10/2023 19:00";
        String endDateString2 = "10/10/2023 20:00";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

        var startDate1 = LocalDateTime.parse(startDateString1, df);
        var endDate1 = LocalDateTime.parse(endDateString1, df);
        var startDate2 = LocalDateTime.parse(startDateString2, df);
        var endDate2 = LocalDateTime.parse(endDateString2, df);
        RegularExamDate date1 = new RegularExamDate(startDate1, endDate1);
        RegularExamDate date2 = new RegularExamDate(startDate2, endDate2);
        Assertions.assertNotEquals(date1, date2);
    }

    @Test
    public void testHashCode_SameDates_ReturnsSameHashCode() {
        String startDateString = "10/10/2023 16:00";
        String endDateString = "10/10/2023 18:00";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

        var startDate = LocalDateTime.parse(startDateString, df);
        var endDate = LocalDateTime.parse(endDateString, df);
        RegularExamDate date1 = new RegularExamDate(startDate, endDate);
        RegularExamDate date2 = new RegularExamDate(startDate, endDate);
        Assertions.assertEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    public void testHashCode_DifferentDates_ReturnsDifferentHashCode() {
        String startDateString1 = "10/10/2023 16:00";
        String endDateString1 = "10/10/2023 18:00";
        String startDateString2 = "10/10/2023 19:00";
        String endDateString2 = "10/10/2023 20:00";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

        var startDate1 = LocalDateTime.parse(startDateString1, df);
        var endDate1 = LocalDateTime.parse(endDateString1, df);
        var startDate2 = LocalDateTime.parse(startDateString2, df);
        var endDate2 = LocalDateTime.parse(endDateString2, df);
        RegularExamDate date1 = new RegularExamDate(startDate1, endDate1);
        RegularExamDate date2 = new RegularExamDate(startDate2, endDate2);
        Assertions.assertNotEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    public void ensureOpenDateAndCloseDateCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new RegularExamDate(null, null));
    }

    public RegularExamDate createRegularExamDateWithDefaultConstructor() {
        return new RegularExamDate() {
            // A subclass with an empty body is created to access the protected constructor
        };
    }
}
