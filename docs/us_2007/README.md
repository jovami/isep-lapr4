US 2007 -- Add/Update Exam Questions to a Repository
====================================================

# Analysis
## Client Specifications

From the project description:

>   The solution should be based on the following type of questions that are
> inspired on the Moodle platform.
>   You should support, as much as possible, the features that are supported in Moodle (...)

The **ANTLR** tool should be used to design the specification grammar
## Business rules

<!-- TODO?? -->

## Unit Tests
### Question

1. ensureMustHaveCourse
2. ensureMustHaveSpecification
3. ensureSpecificationCannotBeNull
4. ensureSpecificationCannotBeEmpty
### Per question type
#### Matching
1. ensureMatchingMustHaveSolution
#### Multiple Choice

1. ensureMultipleChoiceMustHaveDescription
2. ensureMultipleChoiceMustHaveAnswer() {

#### Short Answer
1. ensureShortAnswerMustSpecifyCaseSensitivity
#### Numerical
1. ensureNumericalMustHaveSolution

#### Missing Words
1. ensureMissingWordsMustSpecifyGroup
#### True/False
1. ensureTrueFalseMustHaveSolution


# Design

- Use a layer based approach
- Domain classes:
    + **Question**
    + **QuestionSpecification**
    + **QuestionFactory**
    <!-- + **Question** -->
    <!-- + **QuestionType** (enum) -->
- Controller:
    + **AddExamQuestionsController**
    + **ValidateQuestionSpecificationService**
    <!-- + **GenerateFormativeExamService** -->
- Repository:
    + **QuestionRepository**
    + **TeacherRepository**
    + **StaffMemberRepository**
    + **CourseRepository**
    <!-- + **QuestionRepository** -->

The teacher adding questions **must** select a course they teach in; in order to
decrease the coupling between UI and Domain, **DTO** objects will be used to report to the UI.

Since the grammar for the questions' specification is external to the domain
a **Service** will be created in order to verify that specifications input by users
comply with the aforementioned grammar.

In order to ensure that questions are **always** created with a valid specification,
a **Factory** class will be used to abstract the process.

**NOTE:** Constructors for Question and QuestionSpecification will have **protected** visibility.

## Sequence diagram

The following [sequence diagram](./sd.svg) was created to answer this Use Case:
![Sequence diagram](./sd.svg)

<!-- TODO: is this needed? -->
<!-- ## Grammar -->

<!-- Refer to [the following document](./grammar-reference.md) for information on the formative exam grammar. -->
