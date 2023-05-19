package eapli.base.exam.domain.regular_exam;


import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamDate;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegularExamDateTest {

    private RegularExamDate regularExamDate;


    @Before
    public void BeforeEach(){
        String openDateString = "2023-10-10 16:00";
        String closeDateString = "2023-10-10 18:00";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date openDate = df.parse(openDateString);
            Date closeDate = df.parse(closeDateString);
            regularExamDate =  RegularExamDate.valueOf(openDate,closeDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void valueOf() {
        String startDateString = "2023-10-10 16:00";
        String endDateString = "2023-10-10 18:00";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date startDate = df.parse(startDateString);
            Date endDate = df.parse(endDateString);
            RegularExamDate createdDate = RegularExamDate.valueOf(startDate, endDate);
            Assertions.assertEquals(startDate, createdDate.openDate());
            Assertions.assertEquals(endDate, createdDate.closeDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void setIntervalDate_InvalidDates_ReturnsFalse() {
        String startDateString = "2023-10-10 16:00";
        String endDateString = "2023-10-10 14:00";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date startDate = df.parse(startDateString);
            Date endDate = df.parse(endDateString);
            RegularExamDate createdDate = RegularExamDate.valueOf(startDate, endDate);
            Assertions.assertEquals(createdDate.openDate(),null);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEquals_SameDates_ReturnsTrue() {
        String startDateString = "2023-10-10 16:00";
        String endDateString = "2023-10-10 18:00";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date startDate = df.parse(startDateString);
            Date endDate = df.parse(endDateString);
            RegularExamDate date1 = new RegularExamDate(startDate, endDate);
            RegularExamDate date2 = new RegularExamDate(startDate, endDate);
            Assertions.assertEquals(date1, date2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void openDate_ReturnsCorrectValue() {
        String expectedDateString = "2023-10-10 16:00";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date expectedDate = df.parse(expectedDateString);
            Date openDate = regularExamDate.openDate();
            Assertions.assertEquals(expectedDate, openDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void closeDate_ReturnsCorrectValue() {
        String expectedDateString = "2023-10-10 18:00";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date expectedDate = df.parse(expectedDateString);
            Date closeDate = regularExamDate.closeDate();
            Assertions.assertEquals(expectedDate, closeDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEquals_DifferentDates_ReturnsFalse() {
        String startDateString1 = "2023-10-10 16:00";
        String endDateString1 = "2023-10-10 18:00";
        String startDateString2 = "2023-10-10 19:00";
        String endDateString2 = "2023-10-10 20:00";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date startDate1 = df.parse(startDateString1);
            Date endDate1 = df.parse(endDateString1);
            Date startDate2 = df.parse(startDateString2);
            Date endDate2 = df.parse(endDateString2);
            RegularExamDate date1 = new RegularExamDate(startDate1, endDate1);
            RegularExamDate date2 = new RegularExamDate(startDate2, endDate2);
            Assertions.assertNotEquals(date1, date2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testHashCode_SameDates_ReturnsSameHashCode() {
        String startDateString = "2023-10-10 16:00";
        String endDateString = "2023-10-10 18:00";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date startDate = df.parse(startDateString);
            Date endDate = df.parse(endDateString);
            RegularExamDate date1 = new RegularExamDate(startDate, endDate);
            RegularExamDate date2 = new RegularExamDate(startDate, endDate);
            Assertions.assertEquals(date1.hashCode(), date2.hashCode());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testHashCode_DifferentDates_ReturnsDifferentHashCode() {
        String startDateString1 = "2023-10-10 16:00";
        String endDateString1 = "2023-10-10 18:00";
        String startDateString2 = "2023-10-10 19:00";
        String endDateString2 = "2023-10-10 20:00";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date startDate1 = df.parse(startDateString1);
            Date endDate1 = df.parse(endDateString1);
            Date startDate2 = df.parse(startDateString2);
            Date endDate2 = df.parse(endDateString2);
            RegularExamDate date1 = new RegularExamDate(startDate1, endDate1);
            RegularExamDate date2 = new RegularExamDate(startDate2, endDate2);
            Assertions.assertNotEquals(date1.hashCode(), date2.hashCode());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testToString() {
        String expectedString = "RegularExamDate{openDate=Tue Oct 10 16:00:00 WEST 2023, closeDate=Tue Oct 10 18:00:00 WEST 2023}";
        Assertions.assertEquals(expectedString, regularExamDate.toString());
    }

    @Test
    public void defaultConstructor_OpenDateAndCloseDateAreNull() throws NoSuchFieldException, IllegalAccessException {
        // Create an instance of RegularExamDate using the protected constructor
        RegularExamDate regularExamDate = createRegularExamDateWithDefaultConstructor();

        // Use reflection to access the openDate and closeDate fields
        Field openDateField = RegularExamDate.class.getDeclaredField("openDate");
        openDateField.setAccessible(true);
        Field closeDateField = RegularExamDate.class.getDeclaredField("closeDate");
        closeDateField.setAccessible(true);

        // Verify that the openDate and closeDate fields are null
        Assertions.assertNull(openDateField.get(regularExamDate));
        openDateField.setAccessible(false);
        Assertions.assertNull(closeDateField.get(regularExamDate));
        closeDateField.setAccessible(false);

    }

    public RegularExamDate createRegularExamDateWithDefaultConstructor() {
        return new RegularExamDate() {
            // A subclass with an empty body is created to access the protected constructor
        };
    }
}