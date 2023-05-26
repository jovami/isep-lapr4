package eapli.base.event.recurringPattern.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import eapli.base.event.recurringPattern.domain.RecurringFrequency;
import eapli.base.event.recurringPattern.domain.RecurringPattern;

public class RecurringPatternFreqWeeklyBuilder implements RecurringPatternBuilderInterface {
    private RecurringPattern pattern;

    public RecurringPatternFreqWeeklyBuilder() {
        pattern = new RecurringPattern();

    }

    @Override
    public RecurringPatternFreqWeeklyBuilder withDuration(LocalTime startTime, int duration) {
        pattern.setTime(startTime, duration);
        return this;
    }

    public RecurringPatternFreqWeeklyBuilder withDateInterval(LocalDate startDate, LocalDate endDate) {
        pattern.setDateInterval(startDate, endDate);
        return this;
    }

    public RecurringPatternFreqWeeklyBuilder withDayOfWeek(DayOfWeek dayOfWeek) {
        pattern.setDayOfWeek(dayOfWeek);
        return this;
    }

    @Override
    public RecurringPattern build() {
        pattern.setFrequency(RecurringFrequency.WEEKLY);
        return pattern;
    }

}
