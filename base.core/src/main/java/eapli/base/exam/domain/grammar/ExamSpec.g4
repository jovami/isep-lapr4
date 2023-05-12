grammar ExamSpec;

/*
 * Parser Rules
 */

// Entry point
exam: 'EXAM' LEFT_BRACE title header section+ RIGHT_BRACE ;

// Grammar rules for the title and header sections
title: 'TITLE' COLON STRING;
header: 'HEADER' LEFT_BRACE description? feedback grading RIGHT_BRACE;

// Grammar rules for header properties
description: 'DESCRIPTION' COLON STRING;
feedback: 'FEEDBACK' COLON ('none' | 'on-submission' | 'after-closing');
grading: 'GRADING' COLON  ('none' | 'on-submission' | 'after-closing');

// Grammar rules for the sections of the exam
section: 'SECTION' INT LEFT_BRACE description? question+ RIGHT_BRACE;
question: matching 
        | multiple_choice 
        | short_answer 
        | numerical 
        | missing_words 
        | true_false;

// Grammar rules for matching questions
matching: 'MATCHING' LEFT_BRACE description subquestion+ answer+ matching_solution+ RIGHT_BRACE;
subquestion: 'SUBQUESTION' INT COLON STRING;
answer: 'ANSWER' INT COLON STRING;
matching_solution: 'SOLUTION' INT COLON match LEFT_BRACKET FLOAT RIGHT_BRACKET;
match: INT DASH INT;

// Grammar rules for multiple choice questions
multiple_choice: 'MULTIPLE_CHOICE' LEFT_BRACE choice_type description answer+ numerical_solution+ RIGHT_BRACE;
numerical_solution: 'SOLUTION' INT COLON INT (',' INT)* LEFT_BRACKET FLOAT RIGHT_BRACKET;     
choice_type: 'CHOICE_TYPE' COLON ('single-answer' | 'multiple-answer');

// Grammar rules for short answer questions
short_answer: 'SHORT_ANSWER' LEFT_BRACE description case_sensitive string_solution+ RIGHT_BRACE;
string_solution: 'SOLUTION' INT COLON STRING LEFT_BRACKET FLOAT RIGHT_BRACKET;
case_sensitive: 'CASE_SENSITIVE:' BOOL;

// Grammar rules for numerical questions
numerical: 'NUMERICAL' LEFT_BRACE description error? numerical_solution+ RIGHT_BRACE;
error: 'ERROR' COLON FLOAT;

// Grammar rules for missing words questions
missing_words: 'MISSING_WORDS' LEFT_BRACE description group+ choice+ RIGHT_BRACE;
group: 'GROUP' (STRING | INT) LEFT_BRACE item+ RIGHT_BRACE;
item: 'ITEM' INT COLON STRING;
choice: 'CHOICE' INT LEFT_BRACE numerical_solution+ from_group RIGHT_BRACE;
from_group: 'FROM_GROUP' COLON (STRING | INT);

// Grammar rules for true/false questions
true_false: 'TRUE_FALSE' LEFT_BRACE description boolean_solution RIGHT_BRACE;
boolean_solution: 'SOLUTION' INT COLON BOOL LEFT_BRACKET FLOAT RIGHT_BRACKET;

/*
 * Lexer Rules
 */

// Lexer rules for punctuation and operators
COLON: ':';
LEFT_BRACE: '{';
RIGHT_BRACE: '}';
LEFT_BRACKET: '[';
RIGHT_BRACKET: ']';
DASH: '-';

// Lexer rule for comments
COMMENT: '//' ~[\r\n]* -> skip;

FLOAT: [0-9]+ (('.') [0-9]+);
INT: [0-9]+;
BOOL: ('true' | 'false');
STRING: '"' ~[\n"]* '"';
WS: [ \t\r\n]+ -> skip; // Ignore whitespace 