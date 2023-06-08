package jovami.grammar.impl.antlr;

import eapli.base.exam.domain.question.QuestionType;
import eapli.base.exam.domain.regular_exam.antlr.Question;
import eapli.base.exam.domain.regular_exam.antlr.Section;
import eapli.base.exam.dto.ExamToBeTakenDTO;
import jovami.grammar.impl.antlr.formativeexam.autogen.FormativeExamBaseVisitor;
import jovami.grammar.impl.antlr.formativeexam.autogen.FormativeExamParser.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ANTLRFormativeExamParser extends FormativeExamBaseVisitor<String> {
    private Map<QuestionType, LinkedList<Question>> questionsByType;
    private List<Section> sections = new ArrayList<>();
    private List<Question> questions;
    private ExamToBeTakenDTO dto;

    public ANTLRFormativeExamParser(Map<QuestionType, LinkedList<Question>> questionsByType) {
        this.questionsByType = questionsByType;
    }

    public ExamToBeTakenDTO dto(ParseTree tree) {
        visit(tree);
        return this.dto;
    }

    @Override
    public String visitExam(ExamContext ctx) {
        var title = visitTitle(ctx.title());
        String description = "No description provided";

        var headerCtx = ctx.header();
        if (headerCtx != null)
            description = visitDescription(headerCtx.description());

        visitChildren(ctx);

        this.dto = new ExamToBeTakenDTO(title, description, sections);
        return null;
    }

    @Override
    public String visitTitle(TitleContext ctx) {
        return ctx.value.getText().replaceAll("\"", "");
    }

    @Override
    public String visitDescription(DescriptionContext ctx) {
        return ctx.value.getText().replaceAll("\"", "");
    }

    @Override
    public String visitSection(SectionContext ctx) {
        var id = Integer.parseInt(ctx.id.getText());
        String description = "No description provided";

        var descriptionCtx = ctx.description();
        if (descriptionCtx != null)
            description = visitDescription(ctx.description());
        this.questions = new ArrayList<>();

        visitChildren(ctx);

        this.sections.add(new Section(id, description, this.questions));
        return null;
    }

    @Override
    public String visitQuestion_type(Question_typeContext ctx) {
        var type = QuestionType.valueOf(ctx.type().getText());
        var typeQuestions = this.questionsByType.get(type);

        if (typeQuestions == null || typeQuestions.isEmpty()) {
            throw new IllegalStateException("No questions of type " + type + " available");
        }

        var question = System.currentTimeMillis() % 2 == 0
                ? typeQuestions.poll()
                : typeQuestions.pollLast();

        this.questions.add(question);
        return null;
    }
}
