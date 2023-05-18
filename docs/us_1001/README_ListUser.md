US 1001 -- List users of the system (Teachers and Students, as well as Managers)
==============================

# Analysis

## Business rules

- No business rules for this part of the US_1001.


## Unit tests

- Implemented by the framework and for that reason, no tests where developed

# Design

- Three different lists were needed, each one representing respectively teachers, students and managers.

## Classes

- UI:
    + **ListTeachersStudentsManagersUI**
- Application:
    + **ListUsersController**
    + **ListUsersService**
- Repository:
    + **TeacherRepository**
    + **StudentRepository**
    + **ManagerRepository**

## Sequence Diagram

![diagram](./listUsersSD.svg)
