package jovami.grammar.impl.antlr;

import jovami.grammar.impl.antlr.formativeexam.autogen.FormativeExamLexer;
import jovami.grammar.impl.antlr.formativeexam.autogen.FormativeExamParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import eapli.base.formativeexam.application.parser.FormativeExamValidatorService;

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
