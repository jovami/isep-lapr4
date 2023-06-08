package jovami.grammar.impl.antlr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import eapli.base.exam.application.parser.GradeExamService;
import eapli.base.exam.application.parser.autogen.ExamSpecLexer;
import eapli.base.exam.application.parser.autogen.ExamSpecParser;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.dto.resolution.ExamResolutionDTO;
import eapli.base.examresult.dto.grade.ExamResultDTO;

/**
 * ANTLRExamGrader
 */
public class ANTLRExamGrader implements GradeExamService {

    @Override
    public ExamResultDTO correctExam(RegularExam exam, ExamResolutionDTO resolution) {
        var lexer = new ExamSpecLexer(CharStreams.fromString(
                exam.regularExamSpecification().specificationString()));
        var parser = new ExamSpecParser(new CommonTokenStream(lexer));

        return new ExamSpecGraderVisitor(resolution).dto(parser.exam());
    }

}
