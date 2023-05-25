package eapli.base.event.recurringPattern.application;

import eapli.base.event.recurringPattern.domain.RecurringPattern;

import java.time.LocalTime;

public interface RecurringPatternBuilderInterface {

    RecurringPatternBuilderInterface withDuration(LocalTime time, int duration);

    RecurringPattern build();
}
