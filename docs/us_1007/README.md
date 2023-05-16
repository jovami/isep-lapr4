US 1007 -- As Manager, I want to enroll students in bulk by importing their data using a csv file
==============================

# Analysi

## Business rules

- The username is Unique.


## Unit tests

- Implemented by the framework and for that reason, no tests where developed

# Design

- A system user must have a different username in different applications.
- A more complicated approach was taken, the group made this think about the next sprint, and the
  possibility of the application growing in size and more CSV files needed to be imported, considering
  different roles, with csv files with different headers.

## Classes

- Domain:
    + **UserManagementService**
    + **SystemUser**
- UI:
    + **CSVLoaderStudentsUI**
- Application:
    + **AddUserController**
    + **UserManagementService**
    + **SystemUserBuilder**
    + **CSVLoaderStudentsController**
    + **StudentParser**
    + **CSVHeader**
    + **CSVParser**
    + **CSVReader**
    + **InputReader**
    + **InvalidCSVHeaderException**
- Repository:
    + **ClientUserRepository**

## Sequence Diagram

![diagram](./importStudentsSD.svg)

## Class Diagram

![diagram](./importStudentsCD.svg)
