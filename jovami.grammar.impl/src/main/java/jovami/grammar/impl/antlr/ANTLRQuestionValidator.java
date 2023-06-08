package jovami.grammar.impl.antlr;

import jovami.grammar.impl.antlr.question.autogen.QuestionLexer;
import jovami.grammar.impl.antlr.question.autogen.QuestionParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import eapli.base.question.application.parser.QuestionValidatorService;

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
