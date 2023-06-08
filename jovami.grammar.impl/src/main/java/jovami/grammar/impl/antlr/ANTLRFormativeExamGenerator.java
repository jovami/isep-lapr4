package jovami.grammar.impl.antlr;

import eapli.base.exam.domain.question.QuestionType;
import eapli.base.exam.dto.ExamToBeTakenDTO;
import eapli.base.formativeexam.application.parser.GenerateFormativeExamService;
import eapli.base.formativeexam.domain.FormativeExam;
import eapli.base.question.domain.Question;
import jovami.grammar.impl.antlr.formativeexam.autogen.FormativeExamLexer;
import jovami.grammar.impl.antlr.formativeexam.autogen.FormativeExamParser;
import jovami.grammar.impl.antlr.question.autogen.QuestionLexer;
import jovami.grammar.impl.antlr.question.autogen.QuestionParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ANTLRFormativeExamGenerator implements GenerateFormativeExamService {

    @Override
    public ExamToBeTakenDTO generate(FormativeExam exam, Iterable<Question> questions) {
        var lexer = new FormativeExamLexer(CharStreams.fromString(exam.specification().specification()));
        var parser = new FormativeExamParser(new CommonTokenStream(lexer));
        var questionsByType = randomGroupByType(parsedQuestions(questions));
        return new ANTLRFormativeExamParser(questionsByType).dto(parser.exam());
    }

    private List<eapli.base.exam.domain.regular_exam.antlr.Question> parsedQuestions(Iterable<Question> questions) {
        return StreamSupport.stream(questions.spliterator(), false)
                .map(question -> {
                    var lexer = new QuestionLexer(CharStreams.fromString(question.specification().specification()));
                    var parser = new QuestionParser(new CommonTokenStream(lexer));
                    return new ANTLRQuestionParser(question.identity()).question(parser.question());
                })
                .collect(Collectors.toList());
    }

    private Map<QuestionType, LinkedList<eapli.base.exam.domain.regular_exam.antlr.Question>>
    randomGroupByType(Iterable<eapli.base.exam.domain.regular_exam.antlr.Question> questions) {
        var map = new EnumMap<QuestionType, LinkedList<eapli.base.exam.domain.regular_exam.antlr.Question>>(QuestionType.class);
        for (final var question : questions){
            var type = question.type();
            map.putIfAbsent(type, new LinkedList<>());
            map.get(type).push(question);
        }

        var random = new Random(System.currentTimeMillis() ^ ProcessHandle.current().pid());
        map.forEach((__, list) -> Collections.shuffle(list, random));

        return map;
    }
}
