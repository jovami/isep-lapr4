package jovami.grammar.impl.antlr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.StringUtils;

import eapli.base.exam.application.parser.autogen.ExamSpecBaseVisitor;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.Boolean_solutionContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.Case_sensitiveContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.Choice_typeContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.CombinationsContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.ErrorContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.ExamContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.MatchContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.MatchingContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.Matching_solutionContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.Multiple_choiceContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.NumericalContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.Numerical_solutionContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.QuestionContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.SectionContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.Short_answerContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.String_solutionContext;
import eapli.base.exam.application.parser.autogen.ExamSpecParser.T_numerical_solutionContext;
import eapli.base.exam.dto.resolution.ExamResolutionDTO;
import eapli.base.examresult.dto.grade.ExamResultDTO;
import eapli.base.examresult.dto.grade.ExamResultDTO.Answer;
import eapli.base.examresult.dto.grade.ExamResultDTO.Section;

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

    private float points = 0.f;
    private String feedback = "";

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

        this.result = new ExamResultDTO(this.sections, this.finalPoints);

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
        this.points = 0.f;
        this.feedback = "";

        visitChildren(ctx);

        this.answers.add(this.questionCounter++, new Answer(points, feedback));
        return null;
    }

    // ============================== True/False ==============================//
    @Override
    public String visitBoolean_solution(Boolean_solutionContext ctx) {
        var x = ctx.value.getText();

        var answer = this.resolution.sections()
                .get(sectionCounter)
                .answers()
                .get(questionCounter);

        if (answer.equals(x)) {
            this.points = Float.parseFloat(ctx.points.getText());
            this.finalPoints += points;
            this.feedback = "";
        }

        return null;
    }

    // ============================= Short answer =============================//
    @Override
    public String visitShort_answer(Short_answerContext ctx) {
        var caseSensitive = Boolean.parseBoolean(
                visitCase_sensitive(ctx.case_sensitive()));

        var answer = this.resolution.sections()
                .get(sectionCounter)
                .answers()
                .get(questionCounter);

        if (!caseSensitive)
            answer = StringUtils.stripAccents(answer.toLowerCase());

        this.points = 0;
        this.feedback = "";

        for (var solutionCtx : ctx.string_solution()) {
            var stringSolution = visitString_solution(solutionCtx).split("\n");

            var expected = stringSolution[0];
            if (!caseSensitive)
                expected = StringUtils.stripAccents(expected.toLowerCase());

            var points = Float.parseFloat(stringSolution[1]);

            if (answer.equals(expected)) {
                this.points = points;
                this.finalPoints += points;
                this.feedback = "";
                break;
            }
        }

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

        for (var solutionCtx : ctx.matching_solution()) {
            var solution = visitMatching_solution(solutionCtx).split("\n");
            map.put(solution[0], Float.parseFloat(solution[1]));
        }

        this.feedback = "";
        this.points = 0.f;

        var answers = this.resolution.sections()
                .get(sectionCounter)
                .answers()
                .get(questionCounter)
                .split("\n");

        for (var answer : answers) {
            var points = map.get(answer);
            if (points != null)
                this.points += points;
        }

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

        var answer = Float.parseFloat(this.resolution.sections()
                .get(sectionCounter)
                .answers()
                .get(questionCounter));

        if (answer >= expected - margin && answer <= expected + margin) {
            this.points = points;
            this.finalPoints += points;
            this.feedback = "";
        } else {
            this.points = 0;
            this.feedback = "";
        }

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

        var answer = this.resolution.sections()
                .get(sectionCounter)
                .answers()
                .get(questionCounter);

        if (!singleAnswer) {
            answer = Arrays.stream(answer.split(","))
                    .mapToInt(Integer::parseInt)
                    .sorted()
                    .boxed()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
        }

        var map = new HashMap<String, Float>();
        for (var solutionCtx : ctx.numerical_solution()) {
            var expected = visitNumerical_solution(solutionCtx).split("\n");
            map.put(expected[0], Float.parseFloat(expected[1]));
        }

        var points = map.get(answer);
        if (points != null)
            this.points = points;
        else
            this.points = 0;
        this.feedback = "";
        this.finalPoints += points;

        return null;
    }

    @Override
    public String visitNumerical_solution(Numerical_solutionContext ctx) {
        return String.format("%s\n%s", visit(ctx.combinations()), ctx.points);
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
}
