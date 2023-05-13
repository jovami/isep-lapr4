package eapli.base.event.timetable;

import eapli.base.event.recurringPattern.application.RecurringPatternFreqWeeklyBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.timetable.domain.TimeTable;
import eapli.framework.infrastructure.authz.domain.model.Username;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

public class TimeTableTest {
    private TimeTable timeTable;
    private final String USERNAME = "user";

    @BeforeEach
    void BeforeEach(){
        Username user = Username.valueOf(USERNAME);
        timeTable= new TimeTable(user);
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;

        //RecurringPattern pattern = new RecurringPattern(startDate,endDate,DayOfWeek.MONDAY, RecurringFrequency.WEEKLY, startTime,duration);
        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        timeTable.addPatterns(builder.getPattern());
    }

    @Test
    void testGetPatterns(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        List<RecurringPattern> list = timeTable.getPatterns();
        for (RecurringPattern p:list) {
            Assertions.assertTrue(p.sameAs(builder.getPattern()));
        }
    }

    @Test
    void testOverLap(){
        LocalDate startDate = LocalDate.of(2000,2,10);
        LocalDate endDate = LocalDate.of(2000,2,20);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;


        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        assertFalse(timeTable.checkAvailability(builder.getPattern()));
    }

    @Test
    void testNoneOverLapBeforeDate(){
        LocalDate startDate = LocalDate.of(2000,1,1);
        LocalDate endDate = LocalDate.of(2000,1,30);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        assertTrue(timeTable.checkAvailability(builder.getPattern()));
    }

    @Test
    void testNoneOverLapAfterDate(){
        LocalDate startDate = LocalDate.of(2001,1,1);
        LocalDate endDate = LocalDate.of(2002,1,30);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        assertTrue(timeTable.checkAvailability(builder.getPattern()));
    }
    @Test
    void testIdentity(){
        Username name = Username.valueOf(USERNAME);
        assertEquals(name,timeTable.identity());
    }

    @Test
    void testHasIdentity(){
        Username name = Username.valueOf(USERNAME);
        assertTrue(timeTable.hasIdentity(name));
    }

    @Test
    void testHasIdentityFalse(){
        Username name = Username.valueOf("notUser");
        assertFalse(timeTable.hasIdentity(name));
    }

    @Test
    void testCompareToWithMan(){
        Username name = Username.valueOf("man");
        String man = "man";
        String timetableName = USERNAME;

        assertEquals(timetableName.compareTo(man),timeTable.compareTo(name));
    }

    @Test
    void testCompareTo(){
        Username name = Username.valueOf("Usename");
        String man = "Username";
        String timetableName = USERNAME;

        assertEquals(timetableName.compareTo(man),timeTable.compareTo(name));
    }

    @Test
    void testEqualsSameObject(){
        assertTrue(timeTable.sameAs(timeTable));
    }
    @Test
    void testEqualsNull(){
        assertFalse(timeTable.sameAs(null));
    }

    @Test
    void testEqualsDiffInstance(){
        assertFalse(timeTable.sameAs(new Object()));
    }

    @Test
    void testEqualsDiffUserName(){
        Username user = Username.valueOf("diff");
        TimeTable diff = new TimeTable(user);
        assertFalse(timeTable.sameAs(diff));
    }
    @Test
    void testEqualsSameUserName(){
        Username user = Username.valueOf(USERNAME);
        TimeTable diff = new TimeTable(user);
        assertTrue(timeTable.sameAs(diff));
    }
}
