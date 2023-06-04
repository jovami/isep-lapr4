package jovami.grammar.impl.antlr;

import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import jovami.util.grammar.GrammarValidator;

/**
 * AbstractGrammarValidator
 *
 * @param <L> {@link Lexer} type
 * @param <P> {@link Parser} type
 */
abstract class ANTLRGrammarValidator<L extends Lexer, P extends Parser> implements GrammarValidator {

    /**
     * Inner class to act as a Fail-Fast Error Listener,
     * as we're only concerned with whether the input syntax is valid
     */
    private final class BailErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(
                final Recognizer<?, ?> recognizer,
                final Object offending,
                final int line,
                final int position,
                final String msg,
                final RecognitionException exception) {
            super.syntaxError(
                    recognizer,
                    offending,
                    line,
                    position,
                    msg,
                    exception);
            throw new ParseCancellationException(exception);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean validate(File specFile) throws IOException {
        return this.isValid(CharStreams.fromPath(specFile.toPath()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean validate(String spec) {
        return this.isValid(CharStreams.fromString(spec));
    }

    protected abstract L getLexer(CharStream stream);

    protected abstract P getParser(CommonTokenStream stream);

    protected abstract ParserRuleContext runParser(P parser) throws ParseCancellationException;

    /**
     * @param stream the CharStream to read the tokens from
     * @return whether the provided file obeys the specification grammar
     */
    protected final boolean isValid(CharStream stream) {
        var lexer = getLexer(stream);
        lexer.addErrorListener(new BailErrorListener());

        var parser = getParser(new CommonTokenStream(lexer));
        parser.addErrorListener(new BailErrorListener());

        try {
            runParser(parser);
        } catch (ParseCancellationException e) {
            return false;
        }

        return true;
    }
}
