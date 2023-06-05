Formative Exam Grammar
======================

As the grammar for formative exams is *highly* based off of the grammar for **Regular Exams**,
it is recommended to read [its own documentation](../us_2001/grammar_manual.md) first.

# Creating a formative exam
## Declaring a Formative Exam
Formative exam specifications begin with the **FORMATIVE** and **EXAM** keywords,
followed by a pair of braces (i.e **{** and **}**); the specification is
then written between these braces.

E.g:
```
FORMATIVE EXAM {
    // the specification is then written here //
}
```

**Note:** two forward slashed (i.e **//**) initiate a comment, meaning the remaining of the line will be ignored
when parsing the specification. This can be useful to add notes/reminders to the writer of the specification.

## Defining the Title
To define the title, use the **TITLE** keyword, followed by a colon, and then the
exam title which **must** be in between **quotes**.

```
// Good:
FORMATIVE EXAM {
    TITLE: "Test formative exam"
}

// Incorrect:
FORMATIVE EXAM {
    TITLE: Test formative exam
}
```

## Defining the Header
The header component is **optional**; however, if specified,
a global description can then be added to the formative exam.

Begin the header component by using the **HEADER** keyword, followed by a pair of braces;
the description can then be provided with the **DESCRIPTION** keyword as follows:

```
FORMATIVE EXAM {
    TITLE: "Test formative exam"

    HEADER {
        DESCRIPTION: "formative exam description here"
    }

    // Remaining of the specification //
}
```

The syntax for specifying the description is the same as the one for the exam title;
the only difference being the keyword in use.

Since the header is optional, one can completely omit the header component
if the formative exam need not have a description.

The following is also valid:
```
FORMATIVE EXAM {
    TITLE: "Test formative exam"

    // No header !!
    // Remaining of the specification //
}
```

## Defining Sections

Sections are the main component of the formative exam and they may contain
an **optional** description, as well as a sequence of question types.

The syntax to create a section is similar to the one for the header, the main
difference being that sections have a number attached to it, and the **SECTION**
keyword is used; rather than **HEADER**.

```
FORMATIVE EXAM {
    // other components...
    // ...

    SECTION 1 {
        // section specification

        DESCRIPTION: "This component is optional. Syntax is identical to the global description!"

        // question types here...
    }
}
```

An exam can have **multiple** sections; all with a **distinct** number attached to them.
Just make sure there is **at least one** section.

```
FORMATIVE EXAM {
    // other components...
    // ...

    SECTION 1 {
        // section specification

        DESCRIPTION: "This component is optional. Syntax is identical to the global description!"

        // question types here...
    }

    SECTION 2 {
        // no description this time

        // question types will be covered next!!
    }
}
```

The following ends up being an **invalid** specification, as there are no sections:
```
FORMATIVE EXAM {
    TITLE: "An invalid exam specification. There are no sections!!"
}
```

## Defining question types
Inside each section, a sequence of **at least one** question type(s) must be specified.
Sections with no question types are **invalid** and will result in a parse **error**.

To specify a question type, the **QUESTION** keyword is used, followed by a colon and
one of the **predefined** question types:

1. **MATCHING**
2. **MULTIPLE_CHOICE**
3. **SHORT_ANSWER**
4. **NUMERICAL**
5. **MISSING_WORDS**
6. **TRUE_FALSE**

Question types do not need to (*and cannot be*) in between quotes; instead an identifier
written **exactly** like the ones above must be used.

Additionally, question types may repeat within the same section, as well as across different sections.
However, one should be careful not to repeat the same question number within the same section,
as that will also result in a parse **erorr**.

The following is thus an example of a **valid** formative exam:
```
FORMATIVE EXAM {
    TITLE: "Example Formative exam"

    HEADER {
        DESCRIPTION: "It is not that difficult!!"
    }

    SECTION 1 {
        DESCRIPTION: "This component is optional. Syntax is identical to the global description!"

        QUESTION 1: MATCHING
        QUESTION 2: TRUE_FALSE

        QUESTION 3: NUMERICAL
        QUESTION 4: NUMERICAL

        QUESTION 6: TRUE_FALSE
        QUESTION 7: SHORT_ANSWER
    }

    SECTION 2 {
        // no description this time

        QUESTION 1: MULTIPLE_CHOICE
        QUESTION 2: MULTIPLE_CHOICE
        QUESTION 3: MULTIPLE_CHOICE
        QUESTION 4: MULTIPLE_CHOICE
        QUESTION 5: MISSING_WORDS
    }
}
```
