package eapli.base.event.recurringPattern.application;

import java.time.LocalDate;
import java.time.LocalTime;

import eapli.base.event.recurringPattern.domain.RecurringFrequency;
import eapli.base.event.recurringPattern.domain.RecurringPattern;

public class RecurringPatternFreqOnceBuilder implements RecurringPatternBuilderInterface {
    private RecurringPattern pattern;

    public RecurringPatternFreqOnceBuilder() {
        pattern = new RecurringPattern();
    }

    @Override
    public RecurringPatternFreqOnceBuilder withDuration(LocalTime startTime, int duration) {
        pattern.setTime(startTime, duration);
        return this;
    }

    public RecurringPatternFreqOnceBuilder withDate(LocalDate date) {
        pattern.setDateOnce(date);
        return this;
    }

    @Override
    public RecurringPattern build() {
        pattern.setFrequency(RecurringFrequency.ONCE);
        return pattern;
    }
}
