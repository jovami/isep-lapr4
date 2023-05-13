TimeTable/ RecurringPatterns
==============================

# Analysis

## Business rules

- It is necessary to keep tracking of the events that a given user has scheduled:
    - The implementation of a time table and the recurring patterns serve this purpose

- When Scheduling some types of events(meetings, class, etc... ) it is necessary to let the user know that some of the participant might have other schedules for that given time
- It is possible to have exceptions to a given RecurringPattern, this concept is implemented as a Collection 
in RecurringPattern that stores LocalDate relating the day that it will not occurs the patter

## TimeTable
### Objective:

Keep track of schedules related to a given systemUser(mapped by his username)

- The information related to the schedules is stored in a Collection that contains RecurringPattern

## RecurringPattern
### Objective:

- The events might have a frequency either occurring once, or every week until the endDate specified
- In order to check if 2 recurringPatterns do not overlap with each other use the overLap method which comapres:
  1. Days of week must be the same
  2. Date intervals must converge
  3. Time Must converge
  4. In cases where frequency is once, there must be not an exception where the dates are equals
  Only when all these conditions are met the RecurringPatterns converge with each other

## Unit tests
The RecurringPattern does not accept:

- Date intervals where the startDate is after the endDate

## CheckAvailabilityService

This service uses the previous mentioned implementation to check if for a Collection of Username there is a single user 
that does have overLap in his TimeTable

## Classes

- Domain:
    + **RecurringPattern**
    + **RecurringFrequency**
    + **TimeTable**
- Service:
    + **CheckAvailabilityService**
- Repository:
    + **RecurringPatternRepository**
    + **TimeTableRepository**
- Builder:
    + **RecurringPatternBuilderInterface**
    + **RecurringPatternFreqOnceBuilder**
    + **RecurringPatternFreqWeekBuilder**

