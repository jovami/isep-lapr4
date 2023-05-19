package eapli.base.event.recurringPattern.application;

import eapli.base.event.recurringPattern.domain.RecurringFrequency;
import eapli.base.event.recurringPattern.domain.RecurringPattern;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class RecurringPatternFreqWeeklyBuilder implements RecurringPatternBuilderInterface{
    private RecurringPattern pattern;

    public RecurringPatternFreqWeeklyBuilder(){
        pattern = new RecurringPattern();

    }

    public boolean withDateInterval(LocalDate startDate, LocalDate endDate) {
        return pattern.setDateInterval(startDate,endDate);
    }
    public void withDayOfWeek(DayOfWeek dayOfWeek) {
        pattern.setDayOfWeek(dayOfWeek);
    }

    @Override
    public boolean withDuration(LocalTime startTime, int duration) {
        return pattern.setTime(startTime,duration);
    }

    public RecurringPattern getPattern(){
        pattern.setFrequency(RecurringFrequency.WEEKLY);
        return pattern;
    }

}
