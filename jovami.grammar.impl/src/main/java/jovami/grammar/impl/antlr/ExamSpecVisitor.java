package jovami.grammar.impl.antlr;

import eapli.base.exam.application.parser.autogen.ExamSpecBaseVisitor;
import eapli.base.exam.application.parser.autogen.ExamSpecParser;

public class ExamSpecVisitor extends ExamSpecBaseVisitor<String> {
    @Override
    public String visitExam(ExamSpecParser.ExamContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public String visitTitle(ExamSpecParser.TitleContext ctx) {
        return ctx.value.getText();
    }

    @Override
    public String visitHeader(ExamSpecParser.HeaderContext ctx) {
        var description = visit(ctx.desc);
        var feedback = visit(ctx.feed);
        var grading = visit(ctx.grade);

        return String.format("Header: %s\nFeedback: %s\nGrading: %s", description, feedback, grading);
    }

    @Override
    public String visitDescription(ExamSpecParser.DescriptionContext ctx) {
        return ctx.value.getText();
    }

    @Override
    public String visitFeedback(ExamSpecParser.FeedbackContext ctx) {
        return ctx.type.getText();
    }

    @Override
    public String visitGrading(ExamSpecParser.GradingContext ctx) {
        return ctx.type.getText();
    }
}
