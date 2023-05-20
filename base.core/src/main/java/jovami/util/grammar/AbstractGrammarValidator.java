package jovami.util.grammar;

import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

/**
 * AbstractGrammarValidator
 */
public abstract class AbstractGrammarValidator {

    /**
     * Inner class to act as a Fail-Fast Error Listener,
     * as we're only concerned with whether the input syntax is valid
     */
    public final class BailErrorListener extends BaseErrorListener {
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
     * ANTLR does not provide a generic way of running the parser
     * starting from the initial symbol; so have to make our own
     */
    @FunctionalInterface
    public interface ParserWrapper {

        ParserRuleContext runParser();
    }

    /**
     * Work around for method reference syntax not being able to provide arguments.
     * This interface is meant to be assigned by YourGrammarLexer::new
     */
    @FunctionalInterface
    public interface LexerWrapper {

        Lexer getLexer(CharStream stream);
    }

    /**
     * Wrapper around generating a ParserWrapper; yeah it's ugly, but it works
     */
    @FunctionalInterface
    public interface ParserWrapperFactory {
        ParserWrapper getParser(CommonTokenStream stream, ANTLRErrorListener errListener);
    }

    private final LexerWrapper lexerWrp;

    private final ParserWrapperFactory parserWrp;

    protected AbstractGrammarValidator(LexerWrapper lexer, ParserWrapperFactory parser) {
        this.lexerWrp = lexer;
        this.parserWrp = parser;
    }

    /**
     * @param specFile File path to the formative exam specification
     * @return whether the provided file obeys the specification grammar
     * @throws IOException
     */
    public final boolean validate(File specFile) throws IOException {
        return this.isValid(CharStreams.fromPath(specFile.toPath()));
    }

    /**
     * @param spec string that contains the entire formative exam specification
     * @return whether the provided file obeys the specification grammar
     */
    public final boolean validate(String spec) {
        return this.isValid(CharStreams.fromString(spec));
    }

    /**
     * @param stream the CharStream to read the tokens from
     * @return whether the provided file obeys the specification grammar
     */
    protected boolean isValid(CharStream stream) {
        var lexer = this.lexerWrp.getLexer(stream);
        lexer.addErrorListener(new BailErrorListener());

        var parser = this.parserWrp.getParser(new CommonTokenStream(lexer), new BailErrorListener());

        try {
            parser.runParser();
        } catch (ParseCancellationException e) {
            return false;
        }

        return true;
    }
}
