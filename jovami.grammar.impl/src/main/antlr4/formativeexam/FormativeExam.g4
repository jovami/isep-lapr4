// vim: ft=antlr

grammar FormativeExam;

@header{
package jovami.grammar.impl.antlr.formativeexam.autogen;
}

/* Entry point */
exam: 'FORMATIVE' 'EXAM' LEFT_BRACE title header? section+ RIGHT_BRACE ;

// Grammar rules for the title and header sections
title: 'TITLE' COLON value=STRING;
header: 'HEADER' LEFT_BRACE description RIGHT_BRACE;

// Grammar rules for header properties
description: 'DESCRIPTION' COLON value=STRING;

// Grammar rules for the sections of the exam
section: 'SECTION' id=INT LEFT_BRACE description? question_type+ RIGHT_BRACE;

question_type: 'QUESTION' id=INT COLON type;

type: 'MATCHING'
    | 'MULTIPLE_CHOICE'
    | 'SHORT_ANSWER'
    | 'NUMERICAL'
    | 'MISSING_WORDS'
    | 'TRUE_FALSE'
    ;


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
