# ANTLR4 Grammar for Normal Exam Specification

This ANTLR4 grammar provides a standardized syntax for specifying exams, including their sections and questions, as well as the rules for grading and providing feedback. This grammar can be used by teachers to create exam specifications in a consistent format, which can be parsed and generated programmatically.

# Defining an Exam

The grammar consists of parser rules, which define the structure and syntax of the exam specification. The **exam** rule serves as the entry point for the entire exam specification. To define an exam using this grammar, the exam rule must be used in the following format:

```
exam: 'EXAM' '{' title header section+ '}' ;
```

Here **title**, **header**, and **section** are non-terminal rules defined within the grammar.

## Title

The **title** rule specifies the title of the exam in the format of:

```
title: 'TITLE' ':' STRING;
```

## Header

The header rule defines the header section of the exam and includes:

- an **optional** description;
- feedback properties;
- grading properties.

The description property specifies an optional description of the exam header in the following format:

```
description: 'DESCRIPTION' ':' STRING;
```

The feedback and grading properties define when feedback and grading are given for the exam, respectively. They can take on one of three possible values:

- on-submission;
- after-closing;
- none.

## Sections

The section rule defines the sections of the exam and includes an **optional description** and **question** properties in the following format:

```
section: 'SECTION' INT '{' description? question+ '}';
```

When specifying an exam using this grammar, the exam specification must be formatted in the following way:

```
EXAM {
    TITLE: "Example Exam"
    HEADER {
        DESCRIPTION: "Optional Description" 
        FEEDBACK: on-submission
        GRADING: on-submission
        // Feedback and grading can be: on-submission, after-closing or none
    }

    SECTION 1 {
        DESCRIPTION: "Section 1. OPTIONAL!!" 

        // Questions will be discussed later
    }

    SECTION 2 {
        // You can define as many sections as you want
    }
}
```

## Questions

Questions are part of a section, and they can vary in type:

- Matching;
- Multiple choice;
- Short answer;
- Numerical;
- Missing words;
- True/False;

### Matching Question

Matching questions require the respondent to match a list of subquestions to a corresponding list of answers. The grammar rules for a matching question are defined as follows:

```
matching: 'MATCHING' '{' description subquestion+ answer+ matching_solution+ '}';
subquestion: 'SUBQUESTION' INT ':' STRING;
answer: 'ANSWER' INT ':' STRING;
matching_solution: 'SOLUTION' INT ':' match '[' FLOAT ']';
match: INT '-' INT;
```

Here, **subquestion** defines a subquestion with an integer ID and a string description, **answer** defines an answer with an integer ID and a string value, and **matching_solution** defines both the solution to the matching question and its score. The **match** rule specifies which subquestion corresponds to which answer, and the FLOAT token specifies the score for the match.

The following example showcases the use of these rules to define a matching question:

```
MATCHING {
    DESCRIPTION: "Match the countries with their capital cities"
    
    SUBQUESTION 1: "Portugal"
    SUBQUESTION 2: "Spain"

    ANSWER 1: "Lisbon"
    ANSWER 2: "Madrid"
    ANSWER 3: "Paris"
    ANSWER 4: "Rome"
    
    SOLUTION 1: 1-2 [1.0]
    SOLUTION 2: 2-1 [1.0]
}
```

The solution to the matching question is defined using the following format:

```
SOLUTION (INTEGER ID) : (NUMBER OF SUBQUESTION)-(NUMBER OF ANSWER) [SCORE IN FLOAT]
```

This solution specifies that the correct match for the first subquestion is answer 2 and the correct match for the second subquestion is answer 1, both with a score of 1.0 for each match.

### Multiple Choice Question

Multiple choice questions present a question with a list of possible answers, of which one or more may be correct. The grammar rules for multiple choice questions are defined as follows:

```
multiple_choice: 'MULTIPLE_CHOICE' '{' choice_type description answer+ numerical_solution+ '}';
numerical_solution: 'SOLUTION' INT ':' INT (',' INT)* '[' FLOAT ']';     
choice_type: 'CHOICE_TYPE' ':' ('single-answer' | 'multiple-answer');
```

Here, **multiple_choice** defines the top-level rule for a multiple choice question, which includes a **choice_type** rule that specifies whether the question allows a single answer or multiple answers. The **description** rule specifies the text of the question, and the **answer** rule defines the possible answers, each with an ID and a string value. The **numerical_solution** rule defines both the correct answers to the question and their score.

An example of two multiple choice questions defined using this grammar is:

```
MULTIPLE_CHOICE {
    CHOICE_TYPE: single-answer
    DESCRIPTION: "Who was the first female astronaut to travel to space?"
    
    ANSWER 1: "Sally Ride" 
    ANSWER 2: "Valentina Tereshkova"  
    ANSWER 3: "Mae Jemison"  
    ANSWER 4: "Yuri Gagarin"
    
    SOLUTION 1: 2 [5.0]
}

MULTIPLE_CHOICE {
    CHOICE_TYPE: multiple-answer
    DESCRIPTION: "Which of the following animals are mammals?"
    
    ANSWER 1: "Kangaroo" 
    ANSWER 2: "Turtle" 
    ANSWER 3: "Dolphin"  
    ANSWER 4: "Salmon" 
    
    SOLUTION 1: 1,3 [2.0]
    SOLUTION 2: 1,3,4 [1.5]
    SOLUTION 3: 1,4 [1.0]
    SOLUTION 4: 3,4 [0.5]
    SOLUTION 5: 1 [0.2]
    SOLUTION 6: 3 [0.2]
    SOLUTION 7: 4 [0.1]
}
```

### Short Answer Question

Short answer questions require the respondent to provide a brief text answer to a given question or prompt. The grammar rules for a short answer question are defined as follows:

```
short_answer: 'SHORT_ANSWER' '{' description case_sensitive string_solution+ '}';
string_solution: 'SOLUTION' INT ':' STRING '[' FLOAT ']';
case_sensitive: 'CASE_SENSITIVE:' BOOL;
```

Here, **description** specifies the question that the respondent must answer, **case_sensitive** specifies whether the answer is case-sensitive or not, and **string_solution** defines the correct answer and its score. The score is given as a FLOAT token.

An example of a short answer question defined using this grammar is:

```
SHORT_ANSWER {
    DESCRIPTION: "Where is Casa da Música located?"
    CASE_SENSITIVE: false

    SOLUTION 1: "Boavista" [0.5]
    SOLUTION 2: "Avenida da Boavista" [1.0]
}
```

In this example, the prompt asks for the location of *Casa da Música*, and two possible correct answers are defined, with scores of 0.5 and 1.0. The CASE_SENSITIVE rule specifies that the answer is not case-sensitive.

### Numerical Question

Numerical questions require respondents to provide a numerical answer to a question. The grammar rules for a numerical question are defined as follows:

```
numerical: 'NUMERICAL' '{' description error? numerical_solution+ '}';
error: 'ERROR' ':' FLOAT;
```

Here, **description** defines the question prompt, **error** defines the acceptable error margin for the answer, and **numerical_solution** defines the correct answer(s) and their score(s).

An example of a numerical question defined using this grammar is:

```
NUMERICAL {
    DESCRIPTION: "What is the value of 1+1?"
    ERROR: 0.5

    SOLUTION 1: 2 [1.0] 
}
```

In this example, the correct answer is 2, with an acceptable error margin of 0.5. The score for this answer is 1.0.

### Missing Words Question

In a missing words question, the respondent is presented with a sentence or phrase with one or more missing words. The missing words can be represented by placeholders such as [1], [2], etc. The grammar rules for a missing words question are defined as follows:

```
missing_words: 'MISSING_WORDS' '{' description group+ choice+ '}';
group: 'GROUP' INT '{' item+ '}';
item: 'ITEM' INT ':' STRING;
choice: 'CHOICE' INT '{' numerical_solution+ from_group '}';
from_group: 'FROM_GROUP' ':' INT;
```

Here, **group** specifies a group of possible options for a blank space in the question, identified by either an integer ID or a string ID. Each group contains one or more **item** rules, each with an integer ID and a string value representing a possible option. **choice** specifies a choice with an integer ID, containing one or more **numerical_solution** rules, which specify the correct option ID for the blank space in a given group, and the score for the choice. The **from_group** rule specifies which group of options the choice should be drawn from.

Here's an example of a missing words question defined using this grammar:

```
MISSING_WORDS {
    DESCRIPTION: "The Wright brothers were the first to successfully [1] an airplane."

    GROUP "verbs" {             
        ITEM 1: "fly"
        ITEM 2: "drive"
        ITEM 3: "swim"
    }

    CHOICE 1 {                  
        SOLUTION 1: 1 [1.5] 
        FROM_GROUP: "verbs"    
    }
}   
```

### True/False Question

A true/false question is a type of question where a statement is presented to the respondent, and they must select either "true" or "false" to indicate whether they believe the statement to be correct or incorrect. The grammar rules for a true/false question are defined as follows:

```
true_false: 'TRUE_FALSE' '{' description boolean_solution '}';
boolean_solution: 'SOLUTION' INT ':' BOOL '[' FLOAT ']';
```

Here, **description** refers to the statement presented to the respondent, and **boolean_solution** defines the solution to the true/false question and its score. The BOOL token specifies whether the correct answer is "true" or "false," and the FLOAT token specifies the score for the correct answer.

An example of a true/false question defined using this grammar is:

```
TRUE_FALSE {
    DESCRIPTION: "Water boils at 100 degrees Celsius."
    SOLUTION 1: true [1.0]
}
```

In this example, the statement presented is "Water boils at 100 degrees Celsius," and the correct answer is "true," with a score of 1.0.

## Lexer Rules

Our lexer has a set of rules that define how to identify different types of tokens in the file.
First, there are rules for punctuation and operators such as colons, left and right braces, left and right brackets, and dashes. These are defined as the tokens **COLON**, **LEFT_BRACE**, **RIGHT_BRACE**, **LEFT_BRACKET**, **RIGHT_BRACKET**, and **DASH** respectively.

Next, there is a rule for comments, which in this case is denoted by the double forward slash **//** followed by any characters that are not line breaks. This rule is defined as **COMMENT**, and it is set to be skipped so that it doesn't generate any tokens.

There's also rules for different types of values. These include **FLOAT** for floating-point numbers, **INT** for integers, **BOOL** for boolean values (**true** or **false**), and **STRING** for strings enclosed in double quotes.

Finally, there is a rule for whitespace, which is defined as any combination of spaces, tabs, and line breaks. This rule is defined as **WS**, and it is set to be skipped so that it doesn't generate any tokens.

# Full Example

```
EXAM {
    TITLE: "Example Exam"
    HEADER {
        DESCRIPTION: "Optional Description" 
        FEEDBACK: on-submission
        GRADING: on-submission
    }

    SECTION 1 {
        DESCRIPTION: "Section 1. OPTIONAL!!" 

        MATCHING {
            DESCRIPTION: "Match the countries with their capital cities"
            
            SUBQUESTION 1: "Portugal"
            SUBQUESTION 2: "Spain"
            SUBQUESTION 3: "France"
            SUBQUESTION 4: "Italy"
            
            ANSWER 1: "Lisbon"
            ANSWER 2: "Madrid"
            ANSWER 3: "Paris"
            ANSWER 4: "Rome"
            
            SOLUTION 1: 1-2 [1.0]
            SOLUTION 2: 2-1 [1.0]
            SOLUTION 3: 3-3 [1.0]
            SOLUTION 4: 4-4 [1.0]
        }

        MULTIPLE_CHOICE {
            CHOICE_TYPE: single-answer
            DESCRIPTION: "Who was the first female astronaut to travel to space?"
            
            ANSWER 1: "Sally Ride" 
            ANSWER 2: "Valentina Tereshkova"  
            ANSWER 3: "Mae Jemison"  
            ANSWER 4: "Yuri Gagarin"
            
            SOLUTION 1: 2 [5.0]
        }

        MULTIPLE_CHOICE {
            CHOICE_TYPE: multiple-answer
            DESCRIPTION: "Which of the following animals are mammals?"
            
            ANSWER 1: "Kangaroo" 
            ANSWER 2: "Turtle" 
            ANSWER 3: "Dolphin"  
            ANSWER 4: "Salmon" 
            
            SOLUTION 1: 1,3 [2.0]
            SOLUTION 2: 1,3,4 [1.5]
            SOLUTION 3: 1,4 [1.0]
            SOLUTION 4: 3,4 [0.5]
            SOLUTION 5: 1 [0.2]
            SOLUTION 6: 3 [0.2]
            SOLUTION 7: 4 [0.1]
        }
    }

    SECTION 2 {
        DESCRIPTION: "Welcome to section 2! This message is optional." 

        SHORT_ANSWER {
            DESCRIPTION: "Where is Casa da Música located?"
            CASE_SENSITIVE: false

            SOLUTION 1: "Boavista" [0.5]
            SOLUTION 2: "Avenida da Boavista" [1.0]
        }

        NUMERICAL {
            DESCRIPTION: "What is the value of 1+1?"
            ERROR: 0.5

            SOLUTION 1: 2 [1.0] 
        }

        MISSING_WORDS {
            DESCRIPTION: "The Wright brothers were the first to successfully [1] an airplane."

            GROUP "verbs" {             
                ITEM 1: "fly"
                ITEM 2: "drive"
                ITEM 3: "swim"
            }

            CHOICE 1 {                  
                SOLUTION 1: 1 [1.5] 
                FROM_GROUP: "verbs"    
            }
        }      

        TRUE_FALSE {
            DESCRIPTION: "Water boils at 100 degrees Celsius."
            SOLUTION 1: true [1.0]
        }
    }
}
```