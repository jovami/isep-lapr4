package eapli.base.event.recurringPattern;

import eapli.base.event.recurringPattern.application.RecurringPatternFreqOnceBuilder;
import eapli.base.event.recurringPattern.application.RecurringPatternFreqWeeklyBuilder;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class RecurringPatternTest {

    private RecurringPattern pattern;

    @BeforeEach
    void BeforeEach(){
        LocalTime startTime = LocalTime.of(10, 0);
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        pattern=builder.getPattern();

    }

    @Test
    void testDateWithWrongInterval(){
        LocalDate startDate = LocalDate.of(2003,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);

        Assertions.assertFalse(pattern.setDateInterval(startDate,endDate));
    }

    @Test
    void testDateWithAcceptableInterval(){
        LocalDate startDate = LocalDate.of(1999,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);

        Assertions.assertTrue(pattern.setDateInterval(startDate,endDate));
        Assertions.assertEquals(startDate,pattern.startDate());
        Assertions.assertEquals(endDate,pattern.endDate());
    }
    @Test
    void testDurationWithAcceptableInterval(){
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11, 20);
        int duration = 80;

        pattern.setTime(startTime,duration);
        Assertions.assertEquals(startTime,pattern.startTime());
        Assertions.assertEquals(endTime,pattern.endTime());
    }
/*
    @Test
    void testConstructorWithWrongInterval(){
        LocalDate startDate = LocalDate.of(2003,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;
        Assertions.assertThrows(IllegalArgumentException.class,
                    ()->{
                        pattern = new RecurringPattern(startDate,endDate, DayOfWeek.MONDAY, RecurringFrequency.WEEKLY, startTime,duration);
                    });
    }

    @Test
    void testConstructorWithDurationNegative(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = -10;
        Assertions.assertThrows(IllegalArgumentException.class,
                ()->{
                    pattern = new RecurringPattern(startDate,endDate, DayOfWeek.MONDAY, RecurringFrequency.WEEKLY, startTime,duration);
                });
    }


    @Test
    void testConstructorWithDurationNegative2(){
        LocalDate startDate = LocalDate.of(2003,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = -10;
        Assertions.assertThrows(IllegalArgumentException.class,
                ()->{
                    pattern = new RecurringPattern(startDate,endDate, DayOfWeek.MONDAY, RecurringFrequency.WEEKLY, startTime,duration);
                });
    }*/

    @Test
    void testBetweenDatesRight() {
        LocalDate date = LocalDate.of(2000,2,10);
        Assertions.assertTrue(pattern.betweenDates(date));
    }
    @Test
    void testBetweenDatesSameStartDate() {
        LocalDate date = LocalDate.of(2000,2,1);
        Assertions.assertTrue(pattern.betweenDates(date));
    }
    @Test
    void testBetweenDatesSameEndDate() {
        LocalDate date = LocalDate.of(2000,2,28);
        Assertions.assertTrue(pattern.betweenDates(date));
    }

    @Test
    void testBetweenDatesWrongBefore() {
        LocalDate date = LocalDate.of(2000,1,10);
        Assertions.assertFalse(pattern.betweenDates(date));
    }

    @Test
    void testBetweenDatesWrongAfter() {
        LocalDate date = LocalDate.of(2000, 3   , 10);
        Assertions.assertFalse(pattern.betweenDates(date));
    }

    @Test
    void testSetNegativeDurationMinutes() {
        LocalTime time = LocalTime.of(10, 0);
        int duration = -10;
        Assertions.assertFalse(pattern.setTime(time,duration));
    }

    @Test
    void testDurationMinutes() {
        LocalTime time = LocalTime.of(10, 0);
        int duration = 100;
        Assertions.assertTrue(pattern.setTime(time,duration));
        Assertions.assertEquals(100,pattern.duration());
    }

    @Test
    void testDayOfWeek() {
        Assertions.assertEquals(DayOfWeek.MONDAY,pattern.dayOfWeek());
    }

    @Test
    void testCompareToBigger() {
        Assertions.assertEquals(-1,pattern.compareTo(100));
    }

    @Test
    void testCompareToSmaller() {
        Assertions.assertEquals(1,pattern.compareTo(-10));
    }

    @Test
    void testCompareToEqual() {
        Assertions.assertEquals(0,pattern.compareTo(0));
    }

    @Test
    void testHasIdentity() {
        Assertions.assertTrue(pattern.hasIdentity(0));
    }
    @Test
    void testHasNotIdentity() {
        Assertions.assertFalse(pattern.hasIdentity(10));
    }


    @Test
    void testOverLapBeforeTime(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(11, 0);
        int duration = 120;


        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        RecurringPattern pCompare=builder.getPattern();
        Assertions.assertTrue(pattern.overLapTime(pCompare));
    }

    @Test
    void testOverLapDiffDayOfWeek(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(11, 0);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.THURSDAY);
        builder.setDateInterval(startDate,endDate);
        RecurringPattern pCompare=builder.getPattern();

        Assertions.assertFalse(pattern.overLap(pCompare));
    }
    @Test
    void testOverLapAfterTime(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(10, 30);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        RecurringPattern pCompare=builder.getPattern();
        Assertions.assertTrue(pattern.overLapTime(pCompare));
    }

    @Test
    void testNonOverLapAfterTime(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(13, 0);
        int duration = 120;


        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        RecurringPattern pCompare=builder.getPattern();
        Assertions.assertFalse(pattern.overLap(pCompare));
    }
    @Test
    void testNonOverLapBeforeTime(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(7, 0);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        RecurringPattern pCompare=builder.getPattern();
        Assertions.assertFalse(pattern.overLapTime(pCompare));
    }

    @Test
    void testNonOverLapBeforeDate(){
        LocalDate startDate = LocalDate.of(2000,1,1);
        LocalDate endDate = LocalDate.of(2000,1,10);

        Assertions.assertFalse(pattern.overLapDateInterval(startDate,endDate));
    }

    @Test
    void testNonOverLapAfterDate(){
        LocalDate startDate = LocalDate.of(2000,3,1);
        LocalDate endDate = LocalDate.of(2000,3,28);
        Assertions.assertFalse(pattern.overLapDateInterval(startDate,endDate));
    }

    @Test
    void testOverLapBeforeDate(){
        LocalDate startDate = LocalDate.of(2000,2,15);
        LocalDate endDate = LocalDate.of(2000,2,28);
        Assertions.assertTrue(pattern.overLapDateInterval(startDate,endDate));
    }


    @Test
    void testOverLapAfterDate(){
        LocalDate startDate = LocalDate.of(2000,1,1);
        LocalDate endDate = LocalDate.of(2000,2,15);
        Assertions.assertTrue(pattern.overLapDateInterval(startDate,endDate));
    }

    @Test
    void testSameRecurringPatter(){
        Assertions.assertTrue(pattern.sameAs(pattern));
    }

    @Test
    void testSameNull(){
        Assertions.assertFalse(pattern.sameAs(null));
    }

    @Test
    void testSameNoRecurinPatternInstance(){
        Assertions.assertFalse(pattern.sameAs(new Object()));
    }

    @Test
    void testSameParameters(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        RecurringPattern pCompare=builder.getPattern();
        Assertions.assertTrue(pattern.sameAs(pCompare));
    }
    @Test
    void testSameAsDifferentStartTime(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(1, 0);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        RecurringPattern pCompare=builder.getPattern();
        Assertions.assertFalse(pattern.sameAs(pCompare));
    }
    @Test
    void testSameAsDifferentStartTimeDuration(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(1, 0);
        int duration = 10;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        RecurringPattern pCompare=builder.getPattern();
        Assertions.assertFalse(pattern.sameAs(pCompare));
    }
    @Test
    void testSameAsDifferentDuration(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 10;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        RecurringPattern pCompare=builder.getPattern();
        Assertions.assertFalse(pattern.sameAs(pCompare));
    }

    @Test
    void testSameAsDifferentStartDateTimeDuration(){
        LocalDate startDate = LocalDate.of(2000,2,3);
        LocalDate endDate = LocalDate.of(2000,2,28);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        RecurringPattern pCompare=builder.getPattern();
        Assertions.assertFalse(pattern.sameAs(pCompare));
    }


    @Test
    void testSameAsDifferentEndDateTimeDuration(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2001,2,28);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        RecurringPattern pCompare=builder.getPattern();
        Assertions.assertFalse(pattern.sameAs(pCompare));
    }
    @Test
    void testSameAsDifferentDayOfWeek(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2001,2,28);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.THURSDAY);
        builder.setDateInterval(startDate,endDate);
        RecurringPattern pCompare=builder.getPattern();
        Assertions.assertFalse(pattern.sameAs(pCompare));
    }
    @Test
    void testSameAsDifferentFrequency(){
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalTime startTime = LocalTime.of(10, 0);
        int duration = 120;

        RecurringPatternFreqOnceBuilder builder = new RecurringPatternFreqOnceBuilder();
        builder.withDuration(startTime,duration);
        builder.withDate(startDate);
        RecurringPattern pCompare=builder.getPattern();
        Assertions.assertFalse(pattern.sameAs(pCompare));
    }

    @Test
    void testHashCode(){
        LocalTime startTime = LocalTime.of(10, 0);
        LocalDate startDate = LocalDate.of(2000,2,1);
        LocalDate endDate = LocalDate.of(2000,2,28);
        int duration = 120;

        RecurringPatternFreqWeeklyBuilder builder = new RecurringPatternFreqWeeklyBuilder();
        builder.withDuration(startTime,duration);
        builder.withDayOfWeek(DayOfWeek.MONDAY);
        builder.setDateInterval(startDate,endDate);
        RecurringPattern pCompare=builder.getPattern();
        Assertions.assertEquals(pCompare.hashCode(),pattern.hashCode());
    }

    @Test
    void testNonOverLapWithException(){
        LocalTime startTime = LocalTime.of(10, 0);
        LocalDate startDate = LocalDate.of(2000,2,7);
        LocalDate endDate = LocalDate.of(2000,2,7);
        int duration = 120;

        RecurringPatternFreqOnceBuilder builder = new RecurringPatternFreqOnceBuilder();
        builder.withDuration(startTime,duration);
        builder.withDate(startDate);
        RecurringPattern pCompare=builder.getPattern();

        LocalDate exDate = LocalDate.of(2000,2,7);
        pattern.addException(exDate);
        Assertions.assertFalse(pattern.overLap(pCompare));
    }

    @Test
    void testOverLapWithException(){
        LocalTime startTime = LocalTime.of(10, 0);
        LocalDate startDate = LocalDate.of(2000,2,7);
        LocalDate endDate = LocalDate.of(2000,2,7);
        int duration = 120;

        RecurringPatternFreqOnceBuilder builder = new RecurringPatternFreqOnceBuilder();
        builder.withDuration(startTime,duration);
        builder.withDate(startDate);
        RecurringPattern pCompare=builder.getPattern();
        LocalDate exDate = LocalDate.of(2000,2,14);
        pattern.addException(exDate);
        Assertions.assertTrue(pattern.overLap(pCompare));
    }



    @Test
    void testAddExceptionTrue(){
        // 7-2-2000 -> Monday
        LocalDate date = LocalDate.of(2000,2,7);
        Assertions.assertTrue(pattern.addException(date));
    }

    @Test
    void testAddExceptionFalse(){
        LocalDate date = LocalDate.of(2000,2,8);
        Assertions.assertFalse(pattern.addException(date));
    }

    @Test
    void testAddExceptionOutOfBounds(){
        LocalDate date = LocalDate.of(2001,2,8);
        Assertions.assertFalse(pattern.addException(date));
    }



}
