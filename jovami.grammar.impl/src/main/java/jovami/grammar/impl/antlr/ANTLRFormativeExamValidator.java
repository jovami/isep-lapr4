package jovami.grammar.impl.antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import eapli.base.formativeexam.application.parser.FormativeExamValidatorService;
import eapli.base.formativeexam.application.parser.autogen.FormativeExamLexer;
import eapli.base.formativeexam.application.parser.autogen.FormativeExamParser;

/**
 * ValidateFormativeExamSpecification
 */
public final class ANTLRFormativeExamValidator
        extends ANTLRGrammarValidator<FormativeExamLexer, FormativeExamParser>
        implements FormativeExamValidatorService {

    @Override
    protected FormativeExamLexer getLexer(CharStream stream) {
        return new FormativeExamLexer(stream);
    }

    @Override
    protected FormativeExamParser getParser(CommonTokenStream stream) {
        return new FormativeExamParser(stream);
    }

    @Override
    protected ParserRuleContext runParser(FormativeExamParser parser) throws ParseCancellationException {
        return parser.exam();
    }
}
