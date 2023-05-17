TimeTable/ RecurringPatterns
==============================

# Analysis

## Business rules

- It is necessary to keep tracking of the events that a given user has scheduled:

- When Scheduling events(meetings, class, etc... ) it is necessary to let the user know that some of the participant might have other schedules for that given time

- It is possible to have exceptions to a given event


# Design

## TimeTable

Keep track of schedules related to a given systemUser(mapped by his username)

- The information related to the schedules is stored in a Collection that contains RecurringPattern

## TimeTableService
- `checkAvailabilityByUser(SystemUser user, RecurringPattern pattern)` - check if a recurring pattern does overlap with the user timeTables
- `checkAvailability(Collection<SystemUser> names, RecurringPattern pattern)` - check if a recurring pattern does overlap with a list of user
- `schedule(Collection<SystemUser> users, RecurringPattern pattern)` - creates new timeTables relating the pattern and the users

## RecurringPattern

- The events might have a frequency either occurring once, or every week until the endDate specified
  - Using the *builderPattern*(RecurringPatternBuilderInterface) the recurringPattern can represent patterns with both type of frequencies
    - RecurringPatternFreqOnceBuilder - patterns that occur only once
    - RecurringPatternFreqWeeklyBuilder - patterns that occur weekly

- In order to check if 2 recurringPatterns do not overlap with each other use the overLap method which comapres:
  1. Days of week must be the same
  2. Date intervals must converge
  3. Time Must converge
  4. In cases where frequency is once, there must be not an exception where the dates are equals
     Only when all these conditions are met the RecurringPatterns converge with each other

## Unit tests
The RecurringPattern does not accept:

- ensurePatternNonOverlap()
- ensurePatternOverlap()
- ensureSameDateConverges()
- ensureSameHourConverges()

## Classes

- Domain:
  + **RecurringPattern**
  + **RecurringFrequency**
  + **TimeTable**

- Service:
  + **TimeTableService**

This classes will be persisted using RepositoryPattern():
+ **RecurringPatternRepository**
+ **TimeTableRepository**

Both repositories have in memory and database persistence

- Builder:
  + **RecurringPatternBuilderInterface**
  + **RecurringPatternFreqOnceBuilder**
    + **RecurringPatternFreqWeekBuilder**


