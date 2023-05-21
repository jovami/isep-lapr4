US 1007 -- As Manager, I want to enroll students in bulk by importing their data using a csv file
==============================

# Analysis

## Business rules

- The csv file is composed by the following structure : (mecanographicnumber,courseName).
- Students are already registered.
- The course already exists.

## Improvements

- In next sprint the import using a CourseName it's not the best approach , so a value object CourseID will be created
to have a more correct solution for importing the CSV file.

## Unit tests

- No tests needed for csv import.

# Design

- Used a parser to save all information inside csv file.
- Find students in the student repository using mecanographicnumber, and find courses in course repository using courseName,
  after that a new enrollment is created using the student and course founded("new Enrollment(course,student)").
- The new enrollment created is saved in enrollment repository.
- A more complicated approach was taken, the group made this think about the next sprint, and the
  possibility of the application growing in size and more CSV files needed to be imported, considering
  different roles, with csv files with different headers.

## Classes

- Domain:
    + **MecanographicNumber**
    + **CourseName**
    + **Enrollment**
- UI:
    + **CSVLoaderStudentsUI**
- Application:
    + **CSVLoaderStudentsController**
    + **EnrollmentParser**
    + **BulkEnrollStudentsService**
- Repository:
    + **StudentRepository**
    + **CourseRepository**
    + **EnrollmentRepository**
- Utils:
    + **CSVHeader**
    + **CSVReader**
    + **InvalidCSVHeaderException**
- Factory:
    + **RepositoryFactory**
- Persistence:
    + **PersistenceContext**

## Sequence Diagram

![diagram](./importStudentsSD.svg)


