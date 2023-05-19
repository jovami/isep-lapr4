package eapli.base.exam.aplication;

import eapli.base.exam.domain.grammar.ExamSpecLexer;
import eapli.base.exam.domain.grammar.ExamSpecParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.io.File;
import java.io.IOException;

public class ValidateRegularExamSpecificationService {


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
     * @param stream the CharStream to read the tokens from
     * @return whether the provided file obeys the specification grammar
     */
    private boolean isValid(CharStream stream) {
        var lexer = new ExamSpecLexer(stream);
        lexer.addErrorListener(new BailErrorListener());

        var parser = new ExamSpecParser(new CommonTokenStream(lexer));
        parser.addErrorListener(new BailErrorListener());

        try {
            parser.exam();
        } catch (ParseCancellationException e) {
            return false;
        }

        return true;
    }

    /**
     * @param specFile File path to the formative exam specification
     * @return whether the provided file obeys the specification grammar
     * @throws IOException
     */
    public boolean validate(File specFile) throws IOException {
        return this.isValid(CharStreams.fromPath(specFile.toPath()));
    }

    /**
     * @param spec string that contains the entire formative exam specification
     * @return whether the provided file obeys the specification grammar
     */
    public boolean validate(String spec) {
        return this.isValid(CharStreams.fromString(spec));
    }
}
