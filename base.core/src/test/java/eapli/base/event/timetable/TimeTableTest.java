package eapli.base.event.timetable;

import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqWeeklyBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.timetable.domain.TimeTable;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class TimeTableTest {
    private TimeTable timeTable;
    private final String USERNAME = "user";

    private SystemUser userGlobal;
    private RecurringPattern patternGlobal;

    @BeforeEach
    void BeforeEach(){
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        userBuilder.with(USERNAME, "duMMy1", "dummy", "dummy", "a@b.ro").withRoles(BaseRoles.MANAGER).build();
        userGlobal=userBuilder.build();
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;

        //RecurringPattern pattern = new RecurringPattern(startDate,endDate,DayOfWeek.MONDAY, RecurringFrequency.WEEKLY, startTime,duration);
        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.withDateInterval(startDate,endDate);
        patternGlobal = builder.getPattern();

        timeTable= new TimeTable(userGlobal,patternGlobal);
    }

    @Test
    void testGetPattern(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.withDateInterval(startDate,endDate);

        RecurringPattern pattern = timeTable.pattern();
        Assertions.assertTrue(pattern.sameAs(builder.getPattern()));
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
        builder.withDateInterval(startDate,endDate);
        ///assertFalse(timeTable.checkAvailability(builder.getPattern()));
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
        builder.withDateInterval(startDate,endDate);
        //assertTrue(timeTable.checkAvailability(builder.getPattern()));
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
        builder.withDateInterval(startDate,endDate);
        //assertTrue(timeTable.checkAvailability(builder.getPattern()));
    }


    @Test
    void testHasIdentity(){
        assertTrue(timeTable.hasIdentity(0));
    }

    @Test
    void testHasIdentityFalse(){
        assertFalse(timeTable.hasIdentity(10));
    }

    @Test
    void testCompareToWithLower(){
        assertEquals(1,timeTable.compareTo(-10));
    }

    @Test
    void testCompareTo(){

        assertEquals(-1,timeTable.compareTo(1));
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
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        userBuilder.with("diff", "duMMy1", "dummy", "dummy", "a@b.ro").withRoles(BaseRoles.MANAGER).build();
        TimeTable diff = new TimeTable(userBuilder.build(),patternGlobal);
        assertFalse(timeTable.sameAs(diff));
    }
    @Test
    void testEqualsSameUserName(){
        TimeTable diff = new TimeTable(userGlobal,patternGlobal);
        assertTrue(timeTable.sameAs(diff));
    }

    @Test
    void testEqualsSameUserNameDiffPattern(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;


        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.withDateInterval(startDate,endDate);
        RecurringPattern pattern = builder.getPattern();

        TimeTable diff = new TimeTable(userGlobal,pattern);
        assertFalse(timeTable.sameAs(diff));
    }

    @Test
    void testGetUser(){
        assertEquals(userGlobal,timeTable.getUser());
    }
}
