US 1004 -- Open/Close enrollments
==============================

# Analysis

## Business rules

- Any course enrollments can be in state "ENROLL" or "INPROGESS".
- When a course is in state "ENROLL", meaning that the enrollments for that course are open.
- When a course is in state "INPROGESS", meaning that the enrollments for that course are close.

## Unit tests

1. ensureOpenToEnrollmentsCourseCanBeClosed
2. ensureClosedToEnrollmentsCourseCanBeClosed


# Design

Even though this is a single use case, the user interface will be split into two:

1. Open a course --- **OpenEnrollmentUI**
2. Close a course --- **CloseEnrollmentUI**

The **DTO** pattern will be applied in order to decrease the coupling between the UI and
the domain classes.

Both UIs will require the manager to select a course from the existing ones, so a **service**
to **List Courses** will be created.

In order to facilitate the user experience, **OpenEnrollmentUI** will only display courses that
can actually be enrollable (c.f. Business rules); the same applies to **CloseEnrollmentUI**.

To avoid code duplication, the **strategy pattern** will be applied in the **ListCoursesService**,
by creating a `ofStates(states)` that will provide a list of courses whose `state` matches any
in `states`. This makes this service very flexible as it can be repurposed for any other use
case that requires a list of courses with certain states.

For ease of use, the service will provide `enrollable()` method, that internally
call `ofState()`.

## Classes

- Domain:
    + **Course**
    + **CourseState**
- UI:
    + **OpenEnrollmentUI**
    + **CloseEnrollmentUI**
- Controller:
    + **OpenCloseEnrollmentController**
    + **ListCoursesService**
- Repository:
    + **CourseRepository**
- DTO:
    + **CourseAndStateDTO**
    + **CourseAndStateDTOMapper**


## Sequence Diagram

Unlike the UIs, the same sequence diagram will be used for the open and close case,
as they are 99% similar; the only difference being the method names.

![diagram](./sd.svg)
