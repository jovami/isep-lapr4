package eapli.base.event.recurringPattern.application;

import java.time.LocalTime;

public interface RecurringPatternBuilderInterface {

    public boolean withDuration(LocalTime time, int duration);

}
