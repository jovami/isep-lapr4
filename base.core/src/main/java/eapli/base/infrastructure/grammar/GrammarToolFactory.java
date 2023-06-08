package eapli.base.infrastructure.grammar;

import eapli.base.exam.application.parser.GradeExamService;
import eapli.base.exam.application.parser.ParserExamService;
import eapli.base.exam.application.parser.RegularExamValidatorService;
import eapli.base.formativeexam.application.parser.FormativeExamValidatorService;
import eapli.base.formativeexam.application.parser.GenerateFormativeExamService;
import eapli.base.formativeexam.application.parser.GradeFormativeExamService;
import eapli.base.question.application.parser.QuestionValidatorService;

/**
 * GrammarToolFactory
 */
public interface GrammarToolFactory {

    FormativeExamValidatorService formativeExamValidator();

    RegularExamValidatorService regularExamValidator();

    QuestionValidatorService questionValidator();

    ParserExamService examParserService();

    GradeExamService examGrader();

    GenerateFormativeExamService formativeExamGenerator();

    GradeFormativeExamService formativeExamGrader();
}
