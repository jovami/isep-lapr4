package jovami.grammar.impl.antlr;

import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import eapli.base.exam.dto.ExamToBeTakenDTO;
import eapli.base.formativeexam.application.parser.GenerateFormativeExamService;
import eapli.base.formativeexam.domain.FormativeExam;
import eapli.base.question.domain.Question;
import eapli.base.question.domain.QuestionType;
import eapli.base.question.dto.AbstractQuestionDTO;
import jovami.grammar.impl.antlr.formativeexam.autogen.FormativeExamLexer;
import jovami.grammar.impl.antlr.formativeexam.autogen.FormativeExamParser;
import jovami.grammar.impl.antlr.question.autogen.QuestionLexer;
import jovami.grammar.impl.antlr.question.autogen.QuestionParser;

public final class ANTLRFormativeExamGenerator implements GenerateFormativeExamService {

    @Override
    public ExamToBeTakenDTO generate(FormativeExam exam, Iterable<Question> questions) {
        var lexer = new FormativeExamLexer(CharStreams.fromString(exam.specification().specification()));
        var parser = new FormativeExamParser(new CommonTokenStream(lexer));
        var questionsByType = randomGroupByType(parseQuestions(questions));
        return new ANTLRFormativeExamParser(questionsByType).dto(parser.exam());
    }

    private List<AbstractQuestionDTO> parseQuestions(Iterable<Question> questions) {
        return StreamSupport.stream(questions.spliterator(), false)
                .map(this::parseQuestion)
                .collect(Collectors.toList());
    }

    private AbstractQuestionDTO parseQuestion(Question question) {
        var lexer = new QuestionLexer(CharStreams.fromString(question.specification().specification()));
        var parser = new QuestionParser(new CommonTokenStream(lexer));
        return new ANTLRQuestionParser(question.identity()).question(parser.question());
    }

    private Map<QuestionType, LinkedList<AbstractQuestionDTO>> randomGroupByType(
            Iterable<AbstractQuestionDTO> questions) {

        var map = new EnumMap<QuestionType, LinkedList<AbstractQuestionDTO>>(QuestionType.class);
        for (final var question : questions) {
            var type = question.getType();
            map.putIfAbsent(type, new LinkedList<>());
            map.get(type).push(question);
        }

        var random = new Random(System.currentTimeMillis() ^ ProcessHandle.current().pid());
        map.forEach((__, list) -> Collections.shuffle(list, random));

        return map;
    }
}
