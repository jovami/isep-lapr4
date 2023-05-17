US 4001 -- Create Course
========================================================

# Requirements
- Create courses
- Consider different editions as being different courses
- Only managers execute this functionality

# Analysis

## Business rules

Course is created in close state

The manager provides the minimal requirements to create a course:
 - course code (unique)
 - course name
 - description
 - start date
 - end date


The start and end date date
    - start date before end date
    - neither are related to the past

It is also possible to set minimum and maximum capacity of the course:
    - minimum capacity must be lower than maximum


# Design

## Patterns
- RepositoryPattern : in order to persist the courses created:
  - CourseRepositoryFactory
    - JpaCourseRepositoryFactory (persist in database)
    - InMemoryRepositoryFactory (persist in memory)

## Classes
- Domain:
    + **Course**
        * **CourseCode** 
        * **CourseName**
        * **CourseDescription**
        * **CourseState**
        * **CourseDuration**
        * **CourseCapacity**

- Repository:
    + **CourseRepository**
    
- Controller: 
    * **CreateCourseController**



##  Unit tests - PLANNING

- ensureNotAllowWrongDateInterval
- ensureNotAllowDatesFromPast
- ensureNotAllowWrongCapacities



## Sequence diagram
![SD-US4001](./SD.svg)
