package jovami.grammar.impl.antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import eapli.base.question.application.parser.QuestionValidatorService;
import eapli.base.question.application.parser.autogen.QuestionLexer;
import eapli.base.question.application.parser.autogen.QuestionParser;

/**
 * ValidateQuestionSpecification
 */
public final class ANTLRQuestionValidator
        extends ANTLRGrammarValidator<QuestionLexer, QuestionParser>
        implements QuestionValidatorService {

    @Override
    protected QuestionLexer getLexer(CharStream stream) {
        return new QuestionLexer(stream);
    }

    @Override
    protected QuestionParser getParser(CommonTokenStream stream) {
        return new QuestionParser(stream);
    }

    @Override
    protected ParserRuleContext runParser(QuestionParser parser) throws ParseCancellationException {
        return parser.question();
    }
}
