US 2005 -- As Student, I want to view a list of my grades
=========================================================

# Analysis
## Business rules
This functionality has to follow specific business rules for it to work
as intended, those business rules are regarding the grading property of the
exam, there are **three** possible properties:

- **None** (the exam doesn't have a grade)
- **On submission** (the grade is released right after the student submits
an exam)
- **After closing** (the grade is only released after the closing date of
the exam has occurred)

Following these rules, when a student asks for the system to display their
grades, the system should only display the **available** grades.

## Unit tests

In order to accurately test this functionality, we need to interact
with the Aggregate Root repositories, meaning unit tests aren't the best approach here.

Instead, integration tests should be performed.

# Design
To better answer this problem a service named **ListExamResultsService** will be 
implemented with the following method added to it:

- `regularExamResultsBasedOnGradingProperties()` 

This method will perform the
needed database search operations in order to find the available exam results, 
following the business rules:

  1. Include results with the *on-submission* property.
  2. Include results with the *after-closing* property **only if the closing date** has already passed. 

The **DTO pattern** will be used to display the grades in the UI, alongside with the course from each
exam belongs to.

## Classes
- Domain:
    + **ExamResult**
    + **ExamGrade**
    + **ExamGradeProperties**
    + **CourseId**
    + **Student**
    + **SystemUser**
- Controller:
    + **ListStudentGradesController**
    + **ListExamResultsService**
- Repository:
    + **StudentRepository**
    + **ExamResultRepository**
- DTO:
    + **ExamGradeAndCourseDTO**
    + **ExamGradeAndCourseDTOMapper**

## Sequence diagram
![sd](./sd.svg)