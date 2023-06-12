package jovami.grammar.impl.antlr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.StringUtils;

import eapli.base.exam.dto.resolution.ExamResolutionDTO;
import eapli.base.examresult.dto.grade.ExamResultDTO;
import eapli.base.examresult.dto.grade.ExamResultDTO.Answer;
import eapli.base.examresult.dto.grade.ExamResultDTO.Section;
import jovami.grammar.impl.antlr.exam.autogen.ExamSpecBaseVisitor;
import jovami.grammar.impl.antlr.exam.autogen.ExamSpecParser.*;

/**
 * ExamSpecGraderVisitor
 * TODO: add proper documentation
 */
final class ExamSpecGraderVisitor extends ExamSpecBaseVisitor<String> {

    private final ExamResolutionDTO resolution;
    private ExamResultDTO result;

    private final List<Section> sections;
    private List<Answer> answers;

    private float finalPoints;
    private float maxPoints = 0;

    private float points = 0.f;
    private String feedback = "";
    private boolean isCorrect;
    private static final String DEFAULT_FEEDBACK = "No feedback provided";

    private int sectionCounter;
    private int questionCounter;

    public ExamSpecGraderVisitor(ExamResolutionDTO resolution) {
        super();
        this.resolution = resolution;
        this.result = null;

        this.sectionCounter = 0;
        this.questionCounter = 0;
        this.finalPoints = 0.f;

        this.sections = new ArrayList<>();
        this.answers = new ArrayList<>();
    }

    // HACK: do this the proper way
    public ExamResultDTO dto(ParseTree tree) {
        visit(tree);
        return this.result;
    }

    @Override
    public String visitExam(ExamContext ctx) {
        // TODO: visit header?

        ctx.section().forEach(this::visit);
        // visitChildren(ctx);

        this.result = new ExamResultDTO(this.sections, this.finalPoints, this.maxPoints);

        return null;
    }

    @Override
    public String visitSection(SectionContext ctx) {
        this.questionCounter = 0;
        //

        ctx.question().forEach(this::visit);

        //
        this.sections.add(this.sectionCounter++, new Section(this.answers));
        this.answers = new ArrayList<>();
        return null;
    }

    @Override
    public String visitQuestion(QuestionContext ctx) {
        this.isCorrect = false;
        this.points = 0.f;
        this.feedback = DEFAULT_FEEDBACK;

        visitChildren(ctx);

        this.answers.add(this.questionCounter++, new Answer(points, feedback));
        return null;
    }

    @Override
    public String visitFeedback_text(Feedback_textContext ctx) {
        var combinationCtx = ctx.feedback_combination();

        var correctCtx = combinationCtx.correct_answer();
        var wrongCtx = combinationCtx.wrong_answer();

        String feedback;
        if (isCorrect)
            feedback = correctCtx != null ? correctCtx.value.getText().replaceAll("\"", "") : DEFAULT_FEEDBACK;
        else
            feedback = wrongCtx != null ? wrongCtx.value.getText().replaceAll("\"", "") : DEFAULT_FEEDBACK;

        return feedback;
    }

    // ============================== True/False ==============================//
    @Override
    public String visitTrue_false(True_falseContext ctx) {
        visitBoolean_solution(ctx.boolean_solution());

        var feedbackCtx = ctx.feedback_text();
        if (feedbackCtx != null)
            this.feedback = visitFeedback_text(feedbackCtx);

        return null;
    }

    @Override
    public String visitBoolean_solution(Boolean_solutionContext ctx) {
        var x = ctx.value.getText();

        var answer = this.resolution.getSections()
                .get(sectionCounter)
                .getAnswers()
                .get(questionCounter);

        var points = Float.parseFloat(ctx.points.getText());
        this.maxPoints += points;

        if (answer.equals(x)) {
            this.points = points;
            this.finalPoints += this.points;
            this.isCorrect = true;
        }

        return null;
    }

    // ============================= Short answer =============================//
    @Override
    public String visitShort_answer(Short_answerContext ctx) {
        var caseSensitive = Boolean.parseBoolean(
                visitCase_sensitive(ctx.case_sensitive()));

        var answer = this.resolution.getSections()
                .get(sectionCounter)
                .getAnswers()
                .get(questionCounter);

        if (!caseSensitive)
            answer = StringUtils.stripAccents(answer.toLowerCase());

        this.points = 0;

        for (var solutionCtx : ctx.string_solution()) {
            var stringSolution = visitString_solution(solutionCtx).split("\n");

            var expected = stringSolution[0];
            if (!caseSensitive)
                expected = StringUtils.stripAccents(expected.toLowerCase());

            var points = Float.parseFloat(stringSolution[1]);

            if (answer.equals(expected)) {
                this.points = points;
                this.finalPoints += points;
                this.isCorrect = true;
                break;
            }
        }

        ctx.string_solution()
                .stream()
                .mapToDouble(node -> Float.parseFloat(visitString_solution(node).split("\n")[1]))
                .max()
                .ifPresent(max -> this.maxPoints += max);

        var feedbackCtx = ctx.feedback_text();
        if (feedbackCtx != null)
            this.feedback = visitFeedback_text(feedbackCtx);

        return null;
    }

    @Override
    public String visitCase_sensitive(Case_sensitiveContext ctx) {
        return ctx.value.getText();
    }

    @Override
    public String visitString_solution(String_solutionContext ctx) {
        var value = ctx.value.getText().replaceAll("\"", "");

        return String.format("%s\n%s", value, ctx.points.getText());
    }

    // ============================== Matching ==============================//
    @Override
    public String visitMatching(MatchingContext ctx) {

        var map = new HashMap<String, Float>();

        var max = 0.f;
        for (var solutionCtx : ctx.matching_solution()) {
            var solution = visitMatching_solution(solutionCtx).split("\n");
            var points = Float.parseFloat(solution[1]);
            max += points;

            map.put(solution[0], points);
        }
        this.maxPoints += max;

        this.points = 0.f;

        var answers = this.resolution.getSections()
                .get(sectionCounter)
                .getAnswers()
                .get(questionCounter)
                .split("\n");

        for (var answer : answers) {
            var points = map.get(answer);
            if (points != null)
                this.points += points;
        }

        if (this.points == max)
            this.isCorrect = true;

        var feedbackCtx = ctx.feedback_text();
        if (feedbackCtx != null)
            this.feedback = visitFeedback_text(feedbackCtx);

        this.finalPoints += this.points;
        return null;
    }

    @Override
    public String visitMatching_solution(Matching_solutionContext ctx) {
        return String.format("%s\n%s",
                visitMatch(ctx.match()),
                Float.parseFloat(ctx.points.getText()));
    }

    @Override
    public String visitMatch(MatchContext ctx) {
        return String.format("%s-%s",
                ctx.subquestion_id.getText(),
                ctx.answer_id.getText());
    }

    // ============================== Numerical ==============================//
    @Override
    public String visitNumerical(NumericalContext ctx) {
        var margin = Optional.ofNullable(ctx.error())
                .map(node -> Float.parseFloat(visitError(node)))
                .orElse(0.f);

        var numericalSolution = visitT_numerical_solution(ctx.t_numerical_solution())
                .split("\n");
        var expected = Float.parseFloat(numericalSolution[0]);
        var points = Float.parseFloat(numericalSolution[1]);
        this.maxPoints += points;

        var answer = Float.parseFloat(this.resolution.getSections()
                .get(sectionCounter)
                .getAnswers()
                .get(questionCounter));

        if (answer >= expected - margin && answer <= expected + margin) {
            this.points = points;
            this.finalPoints += points;
            this.isCorrect = true;
        } else {
            this.points = 0;
        }

        var feedbackCtx = ctx.feedback_text();
        if (feedbackCtx != null)
            this.feedback = visitFeedback_text(feedbackCtx);

        return null;
    }

    @Override
    public String visitError(ErrorContext ctx) {
        return ctx.value.getText();
    }

    @Override
    public String visitT_numerical_solution(T_numerical_solutionContext ctx) {
        return String.format("%s\n%s", ctx.value.getText(), ctx.points.getText());
    }

    // =========================== Multiple choice ===========================//
    @Override
    public String visitMultiple_choice(Multiple_choiceContext ctx) {
        var singleAnswer = visitChoice_type(ctx.choice_type()).equals("single-answer");

        var answer = this.resolution.getSections()
                .get(sectionCounter)
                .getAnswers()
                .get(questionCounter);

        if (!singleAnswer) {
            answer = Arrays.stream(answer.split("\n"))
                    .mapToInt(Integer::parseInt)
                    .sorted()
                    .boxed()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
        }

        var map = new HashMap<String, Float>();
        var maxPoints = 0.f;
        for (var solutionCtx : ctx.numerical_solution()) {
            var expected = visitNumerical_solution(solutionCtx).split("\n");
            var points = Float.parseFloat(expected[1]);
            if (points > maxPoints)
                maxPoints = points;

            map.put(expected[0], points);
        }
        this.maxPoints += maxPoints;

        var points = map.get(answer);
        if (points == null)
            points = 0.f;
        else if (points == maxPoints)
            this.isCorrect = true;

        this.points = points;
        this.finalPoints += points;

        var feedbackCtx = ctx.feedback_text();
        if (feedbackCtx != null)
            this.feedback = visitFeedback_text(feedbackCtx);

        return null;
    }

    @Override
    public String visitNumerical_solution(Numerical_solutionContext ctx) {
        return String.format("%s\n%s", visit(ctx.combinations()), ctx.points.getText());
    }

    @Override
    public String visitChoice_type(Choice_typeContext ctx) {
        return ctx.value.getText();
    }

    @Override
    public String visitCombinations(CombinationsContext ctx) {
        return ctx.INT().stream()
                .mapToInt(node -> Integer.parseInt(node.getText()))
                .sorted()
                .boxed()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    // ============================ Missing Words ============================//

    @Override
    public String visitMissing_words(Missing_wordsContext ctx) {
        var map = new HashMap<Integer, HashMap<String, Float>>();
        var max = 0.f;

        for (var choice : ctx.choice()) {
            var id = Integer.parseInt(choice.id.getText());
            var solutions = new HashMap<String, Float>();
            var maxPoints = 0.f;

            for (var solutionCtx : choice.string_solution()){
                var solution = visitString_solution(solutionCtx).split("\n");
                var points = Float.parseFloat(solution[1]);
                if (points > maxPoints)
                    maxPoints = points;

                solutions.put(solution[0], points);
            }
            this.maxPoints += maxPoints;
            max += maxPoints;

            map.put(id, solutions);
        }

        var answers = this.resolution.getSections()
                .get(sectionCounter)
                .getAnswers()
                .get(questionCounter)
                .split("\n");

        // TODO: we have to make sure that the missing words gaps start at 1 and are consecutive
        for (int i = 0; i < answers.length; i++) {
            var solution = map.get(i + 1);

            var points = solution.get(answers[i]);
            if (points != null)
                this.points += points;
        }

        if (this.points == max)
            this.isCorrect = true;

        this.finalPoints += points;

        var feedbackCtx = ctx.feedback_text();
        if (feedbackCtx != null)
            this.feedback = visitFeedback_text(feedbackCtx);

        return null;
    }
}
