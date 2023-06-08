package jovami.grammar.impl.antlr;

import eapli.base.exam.application.parser.ParserExamService;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.dto.ExamToBeTakenDTO;
import jovami.grammar.impl.antlr.exam.autogen.ExamSpecLexer;
import jovami.grammar.impl.antlr.exam.autogen.ExamSpecParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class ANTLRExamParser implements ParserExamService {
    @Override
    public ExamToBeTakenDTO generateExam(RegularExam exam) throws IOException {
        var lexer = new ExamSpecLexer(CharStreams.fromFileName("data.txt"));
        var tokens = new CommonTokenStream(lexer);
        var parser = new ExamSpecParser(tokens);
        var tree = parser.exam(); // parse
        new ExamSpecVisitor().visit(tree);

        // TODO: implement
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
