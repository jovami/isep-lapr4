package jovami.grammar.impl.antlr;

import java.util.ArrayList;
import java.util.Map;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import eapli.base.examresult.dto.grade.ExamResultDTO;
import eapli.base.examresult.dto.grade.ExamResultDTO.Answer;
import eapli.base.examresult.dto.grade.ExamResultDTO.Section;
import eapli.base.formativeexam.application.parser.GradeFormativeExamService;
import eapli.base.formativeexam.dto.resolution.FormativeExamResolutionDTO;
import eapli.base.question.domain.Question;
import jovami.grammar.impl.antlr.QuestionGraderVisitor.GradedQuestion;
import jovami.grammar.impl.antlr.question.autogen.QuestionLexer;
import jovami.grammar.impl.antlr.question.autogen.QuestionParser;

public class ANTLRFormativeExamGrader implements GradeFormativeExamService {

    @Override
    public ExamResultDTO correctExam(/* final FormativeExam exam, */
            final FormativeExamResolutionDTO resolution,
            final Map<Long, Question> questions) {

        var maxGrade = 0.f;
        var grade = 0.f;

        final var resultSections = new ArrayList<Section>(resolution.getSectionAnswers().size());

        for (final var section : resolution.getSectionAnswers()) {
            final var resultAnswers = new ArrayList<Answer>(section.getAnswers().size());

            for (final var answer : section.getAnswers()) {
                final var question = questions.get(answer.getQuestionID());

                final var result = gradeQuestion(question, answer.getAnswer());
                final var points = result.points();
                grade += points;
                maxGrade += result.maxPoints();

                resultAnswers.add(new Answer(points, result.maxPoints(), result.feedback()));
            }

            resultSections.add(new Section(resultAnswers));
        }

        return new ExamResultDTO(resultSections, grade, maxGrade);
    }

    private GradedQuestion gradeQuestion(Question question, String givenAnswer) {
        final var lexer = new QuestionLexer(CharStreams.fromString(
                question.specification().specification()));
        final var parser = new QuestionParser(new CommonTokenStream(lexer));

        return new QuestionGraderVisitor(givenAnswer).dto(parser.question());
    }
}
