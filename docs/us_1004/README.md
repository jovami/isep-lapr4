US 1004 -- Open/Close a course
==============================

# Analysis

## Business rules

<!-- - Courses can't be closed if they have never been opened -->
- Any course can be closed
- Once opened, courses cannot go back to the "Opened" state

## Unit tests
1. ensureCourseStateCannotGoBackToOpen
2. ensureCreatedCourseCanBeClosed
3. ensureOpenCourseCanBeClosed
4. ensureOpenToEnrollmentsCourseCanBeClosed
5. ensureClosedToEnrollmentsCourseCanBeClosed


# Design

Even though this is a single use case, the user interface will be split into two:

1. Open a course --- **OpenCourseUI**
2. Close a course --- **CloseCourseUI**

The **DTO** pattern will be applied in order to decrease the coupling between the UI and
the domain classes.

Both UIs will require the manager to select a course from the existing ones, so a **service**
to **List Courses** will be created.

In order to facilitate the user experience, **OpenCourseUI** will only display courses that
can actually be opened (c.f. Business rules); the same applies to **CloseCourseUI**.

To avoid code duplication, the **strategy pattern** will be applied in the **ListCoursesService**,
by creating a `ofStates(states)` that will provide a list of courses whose `state` matches any
in `states`. This makes this service very flexible as it can be repurposed for any other use
case that requires a list of courses with certain states.

For ease of use, the service will provide `openable()` and `closable()` methods, that internally
call `ofStates()`.

## Classes

- Domain:
    + **Course**
    + **CourseState**
- Controller:
    + **OpenCloseCourseController**
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
