package jovami.grammar.impl.antlr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import eapli.base.exam.application.parser.GradeExamService;
import eapli.base.exam.domain.RegularExam;
import eapli.base.exam.dto.CorrectedExamDTO;
import eapli.base.exam.dto.resolution.ExamResolutionDTO;
import jovami.grammar.impl.antlr.exam.autogen.ExamSpecLexer;
import jovami.grammar.impl.antlr.exam.autogen.ExamSpecParser;

/**
 * ANTLRExamGrader
 */
public class ANTLRExamGrader implements GradeExamService {

    @Override
    public CorrectedExamDTO correctExam(RegularExam exam, ExamResolutionDTO resolution) {
        var lexer = new ExamSpecLexer(CharStreams.fromString(
                exam.specification().specification()));
        var parser = new ExamSpecParser(new CommonTokenStream(lexer));

        return new ExamSpecGraderVisitor(resolution).dto(parser.exam());
    }

}
