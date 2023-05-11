US 2008 -- Create/Update Automatic Formative Exams
==================================================

# Analysis
## Client Specifications

- The structure formative exams is similar to regular exams
    + **Title**
    + **Header**
        * textual *description*
        * *feedback* and *grading* are **only** provided at the end.
    + **Sequence of sections**
        * formative exams are composed of **one or more** sections
        * each section *may* have a textual description to appear at the beginning
        * each section has **one or more** questions
    + **No start/end dates**
- When creating the exam specification, the user specifies the **type** of questions to
be inserted.
## Business rules

- The **system** should generate the formative exam based on the specification
- The **system** should randomly create the questions based on the type.
- Questions **may not** repeat within a formative exam.

**NOTE:** "the system should randomly create the questions (...)" is being understood as the
system using the existing question repository to fill in questions based on their type.
## Unit Tests

### Formative Exam
1. formativeExamMustHaveAtLeastOneSection
2. formativeExamSectionMustHaveAtLeastOneQuestion
3. formativeExamQuestionsMayNotRepeat

### Formative Exam Specification
4. specificationMustHaveAtLeastOneSection
5. specificationSectionsMustHaveAtLeastOneQuestionType


# Design

- Use a layer based approach
- Domain classes:
    + **FormativeExam**
    + **FormativeExamSpecification**
    + **Question**
    + **QuestionType** (enum)
- Controller:
    + **CreateFormativeExamController**
    + **GenerateFormativeExamService**
    + **FormativeExamSpecificationBuilder** (based on an *ANTLR* grammar)
- Repository:
    + **FormativeExamRepository**
    + **FormativeExamSpecificationRepository**
    + **QuestionRepository**

**NOTE:** only the necessary aggregates are being listed in the *domain classes*.
For a more complete description refer to the [class diagram](./class-diagram.svg).

<!-- TODO: add grammar documentation -->

The following [sequence diagram](./sd.svg) was created to answer this Use Case:
![Sequence diagram](./sd.svg)

# Test Plan
