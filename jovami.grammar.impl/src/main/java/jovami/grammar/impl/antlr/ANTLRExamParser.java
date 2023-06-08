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
        var lexer = new ExamSpecLexer(CharStreams.fromFileName(
                exam.specification().specificationString()));
        var parser = new ExamSpecParser(new CommonTokenStream(lexer));

        return new ExamSpecVisitor().dto(parser.exam());
    }
}
