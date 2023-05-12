grammar ExamSpecs;

/*
 * Parser Rules
 */

exam: 'EXAM' '{' title header section+ '}' ;

title: 'TITLE' ':' STRING ;

header: 'HEADER' '{' description? feedback_type grade_type '}';

description: 'DESCRIPTION' ':' STRING;

feedback_setting: 'FEEDBACK' ':' ('none' | 'on-submission' | 'after-closing');

grading_setting: 'GRADING' ':' ('none' | 'on-submission' | 'after-closing');

section: 'SECTION' INT '{' description? question+ '}';

question: matching_question
        | multiple_choice_question
        | short_answer_question
        | numerical_question        
        | select_missing_words
        | true_false_question;

matching_question: 'MATCHING' '{' subquestion+ matching_answer+ matching_solution+ '}';

subquestion: 'SUBQUESTION' INT ':' STRING;

matching_answer: 'ANSWER' INT ':' STRING;

matching_solution: 'SOLUTION' INT '-' INT weight; //subquestion_number - answer_number

weight: '(' INT ')';

multiple_choice_question: ('SINGLEANSWER'|'MULTIPLEANSWER') '{' description answer+ '}';

answer: 'ANSWER' INT ':' STRING weight;

short_answer_question: 'SHORTANSWER' '{' description case_sensitive answer+ '}';

case_sensitive: 'CASESENSITIVE' ':' ('true' | 'false');

numerical_question: 'NUMERICAL' '{' description numerical_answer+ '}';

numerical_answer: 'ANSWER' INT ':' number_range weight?;

number_range: number_tolerance? number_exact;

number_tolerance: '(' FLOAT ')';

number_exact: FLOAT;

select_missing_words: 'SELECTMISSINGWORDS' '{' description group+ '}';

group: 'GROUP' INT '{' answer+ '}';

true_false_question: 'TRUEFALSE' '{' description? statement '}';

statement: 'STATEMENT' ':' STRING true_false_answer weight;

true_false_answer: 'ANSWER' ':' ('True' | 'False');

/*
 * Lexer Rules
 */

fragment DIGIT : [0-9];
INT : DIGIT+;
STRING : '"' (~["\r\n] | '\\"')* '"';
WS     : [ \t\r\n]+ -> skip;

