package jovami.grammar.impl.antlr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import eapli.base.exam.application.parser.ParserExamService;
import eapli.base.exam.domain.RegularExam;
import eapli.base.exam.dto.ExamToBeTakenDTO;
import jovami.grammar.impl.antlr.exam.autogen.ExamSpecLexer;
import jovami.grammar.impl.antlr.exam.autogen.ExamSpecParser;

public class ANTLRExamParser implements ParserExamService {
    @Override
    public ExamToBeTakenDTO generateExam(RegularExam exam) {
        var lexer = new ExamSpecLexer(CharStreams.fromString(
                exam.specification().specification()));
        var parser = new ExamSpecParser(new CommonTokenStream(lexer));

        return new ExamSpecVisitor().dto(parser.exam());
    }
}
