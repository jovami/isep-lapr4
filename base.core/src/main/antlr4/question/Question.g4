// vim: ft=antlr

grammar Question;

@header{
package eapli.base.question.application.parser.autogen;
}

/* Entry point */
question: matching
        | multiple_choice
        | short_answer
        | numerical
        | missing_words
        | true_false;

description: 'DESCRIPTION' COLON STRING;

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


/* Lexer Rules */


// Punctuation and operators
COLON: ':';
LEFT_BRACE: '{';
RIGHT_BRACE: '}';
LEFT_BRACKET: '[';
RIGHT_BRACKET: ']';
DASH: '-';

// Comments
COMMENT: '//' ~[\r\n]* -> skip;

// Data types
FLOAT: [0-9]+ (('.') [0-9]+);
INT: [0-9]+;
BOOL: ('true' | 'false');
STRING: '"' ~[\n"]* '"';

// Ignore whitespace
WS: [ \t\r\n]+ -> skip;
