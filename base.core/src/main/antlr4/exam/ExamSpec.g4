// vim: ft=antlr

grammar ExamSpec;

/*
 * Parser Rules
 */

@header{
package eapli.base.exam.application.parser.autogen;
}

// Entry point
exam: 'EXAM' LEFT_BRACE titl=title headr=header sec=section+ RIGHT_BRACE ;

// Grammar rules for the title and header sections
title: 'TITLE' COLON value=STRING;
header: 'HEADER' LEFT_BRACE desc=description? feed=feedback grade=grading RIGHT_BRACE;

// Grammar rules for header properties
description: 'DESCRIPTION' COLON value=STRING;
feedback: 'FEEDBACK' COLON type=('none' | 'on-submission' | 'after-closing');
grading: 'GRADING' COLON  type=('none' | 'on-submission' | 'after-closing');

// Grammar rules for the sections of the exam
section: 'SECTION' INT LEFT_BRACE desc=description? questn=question+ RIGHT_BRACE;
question: matching
        | multiple_choice
        | short_answer
        | numerical
        | missing_words
        | true_false;

// Grammar rules for matching questions
matching: 'MATCHING' LEFT_BRACE desc=description subq=subquestion+ ans=answer+ solution=matching_solution+ RIGHT_BRACE;
subquestion: 'SUBQUESTION' id=INT COLON value=STRING;
answer: 'ANSWER' id=INT COLON value=STRING;
matching_solution: 'SOLUTION' id=INT COLON mtch=match LEFT_BRACKET points=FLOAT RIGHT_BRACKET;
match: subquestion_id=INT DASH answer_id=INT;

// Grammar rules for multiple choice questions
multiple_choice: 'MULTIPLE_CHOICE' LEFT_BRACE type=choice_type desc=description ansr=answer+ solution=numerical_solution+ RIGHT_BRACE;
numerical_solution: 'SOLUTION' id=INT COLON value=a LEFT_BRACKET points=FLOAT RIGHT_BRACKET;
a: val1=INT
 | val2=INT ',' value=a
 ;
choice_type: 'CHOICE_TYPE' COLON type=('single-answer' | 'multiple-answer');

// Grammar rules for short answer questions
short_answer: 'SHORT_ANSWER' LEFT_BRACE desc=description sensitive=case_sensitive solution=string_solution+ RIGHT_BRACE;
string_solution: 'SOLUTION' id=INT COLON value=STRING LEFT_BRACKET points=FLOAT RIGHT_BRACKET;
case_sensitive: 'CASE_SENSITIVE:' value=BOOL;

// Grammar rules for numerical questions
numerical: 'NUMERICAL' LEFT_BRACE desc=description err=error? solution=numerical_solution+ RIGHT_BRACE;
error: 'ERROR' COLON value=FLOAT;

// Grammar rules for missing words questions
missing_words: 'MISSING_WORDS' LEFT_BRACE desc=description grp=group+ chosen=choice+ RIGHT_BRACE;
group: 'GROUP' id=(STRING | INT) LEFT_BRACE itm=item+ RIGHT_BRACE;
item: 'ITEM' id=INT COLON value=STRING;
choice: 'CHOICE' id=INT LEFT_BRACE solution=numerical_solution+ grp=from_group RIGHT_BRACE;
from_group: 'FROM_GROUP' COLON id=(STRING | INT);

// Grammar rules for true/false questions
true_false: 'TRUE_FALSE' LEFT_BRACE desc=description solution=boolean_solution RIGHT_BRACE;
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
