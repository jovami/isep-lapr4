package jovami.grammar.impl.antlr;

import eapli.base.exam.application.parser.RegularExamValidatorService;
import eapli.base.formativeexam.application.parser.FormativeExamValidatorService;
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
}