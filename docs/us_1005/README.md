US1005 - Set Course Teachers
========================================================

# Analysis
## Business rules
- Only the manager must be able to choose the course teachers

## Unit tests

In order to accurately test this functionality, we need to interact
with the Aggregate Root repositories, meaning unit tests aren't the best approach here.

Instead, integration tests should be performed.

# Design

The event(meeting) scheduled will be mostly handle by the **TimeTableService** which will implement:

1. `checkAvailabilityByUser(SystemUser user, RecurringPattern pattern)` --- check user availability for a given pattern
2. `checkAvailability(Iterable users, RecurringPattern pattern)` --- check a list of user availability for a given pattern
3. `createEvent(Iterable users, RecuringPattern pattern)` --- adds the given pattern to the timeTable of all users


## Classes
- Domain:
    + **Course**
    + **StaffMember**
    + **Teacher**
- Controller:
    + **SetCourseTeacher**
- Repository:
    + **TeacherRepository**
    + **StaffRepository**
    + **CourseRepository**


## Sequence diagram

![SD-US1005](./SD.svg)
