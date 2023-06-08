package jovami.grammar.impl.antlr;

import jovami.grammar.impl.antlr.exam.autogen.ExamSpecLexer;
import jovami.grammar.impl.antlr.exam.autogen.ExamSpecParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import eapli.base.exam.application.parser.RegularExamValidatorService;


public class ANTLRRegularExamValidator
        extends ANTLRGrammarValidator<ExamSpecLexer, ExamSpecParser>
        implements RegularExamValidatorService {

    @Override
    protected ExamSpecLexer getLexer(CharStream stream) {
        return new ExamSpecLexer(stream);
    }

    @Override
    protected ExamSpecParser getParser(CommonTokenStream stream) {
        return new ExamSpecParser(stream);
    }

    @Override
    protected ParserRuleContext runParser(ExamSpecParser parser) throws ParseCancellationException {
        return parser.exam();
    }

}
