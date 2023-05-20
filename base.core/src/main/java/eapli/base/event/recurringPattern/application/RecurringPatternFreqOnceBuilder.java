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
    public boolean withDuration(LocalTime startTime, int duration) {
        return pattern.setTime(startTime, duration);
    }

    public void withDate(LocalDate date) {
        pattern.setDateOnce(date);
    }

    public RecurringPattern build() {
        pattern.setFrequency(RecurringFrequency.ONCE);
        return pattern;
    }
}
