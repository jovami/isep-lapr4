// vim: ft=antlr

grammar ExamSpec;

// TODO: update ../question/Question.g4

/*
 * Parser Rules
 */

@header{
package jovami.grammar.impl.antlr.exam.autogen;
}

// Entry point
exam: 'EXAM' LEFT_BRACE title header section+ RIGHT_BRACE ;

// Grammar rules for the title and header sections
title: 'TITLE' COLON value=STRING;
header: 'HEADER' LEFT_BRACE description? feedback grading RIGHT_BRACE;

// Grammar rules for header properties
description: 'DESCRIPTION' COLON value=STRING;
feedback: 'FEEDBACK' COLON value=('none' | 'on-submission' | 'after-closing');
grading: 'GRADING' COLON  value=('none' | 'on-submission' | 'after-closing');

// Feedback
feedback_text: 'FEEDBACK' LEFT_BRACE feedback_combination RIGHT_BRACE;
feedback_combination: wrong_answer? correct_answer
                    | correct_answer? wrong_answer;
wrong_answer: 'WRONG_ANSWER' COLON value=STRING;
correct_answer: 'CORRECT_ANSWER' COLON value=STRING;

// Grammar rules for the sections of the exam
section: 'SECTION' id=INT LEFT_BRACE description? question+ RIGHT_BRACE;
question: matching
        | multiple_choice
        | short_answer
        | numerical
        | missing_words
        | true_false;

// Grammar rules for matching questions
matching: 'MATCHING' LEFT_BRACE description subquestion+ answer+ matching_solution+ feedback_text? RIGHT_BRACE;
subquestion: 'SUBQUESTION' id=INT COLON value=STRING;
answer: 'ANSWER' id=INT COLON value=STRING;
matching_solution: 'SOLUTION' id=INT COLON match LEFT_BRACKET points=FLOAT RIGHT_BRACKET;
match: subquestion_id=INT DASH answer_id=INT;

// Grammar rules for multiple choice questions
multiple_choice: 'MULTIPLE_CHOICE' LEFT_BRACE choice_type description answer+ numerical_solution+ feedback_text? RIGHT_BRACE;
// TODO: change name
numerical_solution: 'SOLUTION' id=INT COLON combinations LEFT_BRACKET points=FLOAT RIGHT_BRACKET;
combinations: INT (',' INT)*;
choice_type: 'CHOICE_TYPE' COLON value=('single-answer' | 'multiple-answer');

// Grammar rules for short answer questions
short_answer: 'SHORT_ANSWER' LEFT_BRACE description case_sensitive string_solution+ feedback_text? RIGHT_BRACE;
string_solution: 'SOLUTION' id=INT COLON value=STRING LEFT_BRACKET points=FLOAT RIGHT_BRACKET;
case_sensitive: 'CASE_SENSITIVE:' value=BOOL;

// Grammar rules for numerical questions
numerical: 'NUMERICAL' LEFT_BRACE description error? t_numerical_solution feedback_text? RIGHT_BRACE;
// TODO: change name
t_numerical_solution: 'SOLUTION' id=INT COLON value=INT LEFT_BRACKET points=FLOAT RIGHT_BRACKET;
error: 'ERROR' COLON value=FLOAT;

// Grammar rules for missing words questions
missing_words: 'MISSING_WORDS' LEFT_BRACE description group+ choice+ feedback_text? RIGHT_BRACE;
group: 'GROUP' id=(STRING | INT) LEFT_BRACE item+ RIGHT_BRACE;
item: 'ITEM' id=INT COLON value=STRING;
choice: 'CHOICE' id=INT LEFT_BRACE string_solution+ from_group RIGHT_BRACE;
from_group: 'FROM_GROUP' COLON id=(STRING | INT);

// Grammar rules for true/false questions
true_false: 'TRUE_FALSE' LEFT_BRACE description boolean_solution feedback_text? RIGHT_BRACE;
boolean_solution: 'SOLUTION' id=INT COLON value=BOOL LEFT_BRACKET points=FLOAT RIGHT_BRACKET;

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
