package jovami.grammar.impl.antlr;

import eapli.base.exam.application.parser.GradeExamService;
import eapli.base.exam.application.parser.ParserExamService;
import eapli.base.exam.application.parser.RegularExamValidatorService;
import eapli.base.formativeexam.application.parser.FormativeExamValidatorService;
import eapli.base.formativeexam.application.parser.GenerateFormativeExamService;
import eapli.base.formativeexam.application.parser.GradeFormativeExamService;
import eapli.base.infrastructure.grammar.GrammarToolFactory;
import eapli.base.question.application.parser.QuestionValidatorService;

/**
 * ANTLRGrammarToolsFactory
 */
public class ANTLRGrammarToolsFactory implements GrammarToolFactory {

    @Override
    public FormativeExamValidatorService formativeExamValidator() {
        return new ANTLRFormativeExamValidator();
    }

    @Override
    public RegularExamValidatorService regularExamValidator() {
        return new ANTLRRegularExamValidator();
    }

    @Override
    public QuestionValidatorService questionValidator() {
        return new ANTLRQuestionValidator();
    }

    @Override
    public ParserExamService examParserService() {
        return new ANTLRExamParser();
    }

    @Override
    public GradeExamService examGrader() {
        return new ANTLRExamGrader();
    }

    @Override
    public GenerateFormativeExamService formativeExamGenerator() {
        return new ANTLRFormativeExamGenerator();
    }

    @Override
    public GradeFormativeExamService formativeExamGrader() {
        return new ANTLRFormativeExamGrader();
    }
}
