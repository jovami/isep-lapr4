package jovami.grammar.impl.antlr;

import eapli.base.exam.dto.ExamToBeTakenDTO;
import eapli.base.exam.dto.ExamToBeTakenDTO.Section;
import eapli.base.question.domain.QuestionType;
import eapli.base.question.dto.AbstractQuestionDTO;
import jovami.grammar.impl.antlr.formativeexam.autogen.FormativeExamBaseVisitor;
import jovami.grammar.impl.antlr.formativeexam.autogen.FormativeExamParser.*;
import jovami.util.grammar.NotEnoughQuestionsException;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ANTLRFormativeExamParser extends FormativeExamBaseVisitor<String> {
    private Map<QuestionType, LinkedList<AbstractQuestionDTO>> questionsByType;
    private List<Section> sections = new ArrayList<>();
    private List<AbstractQuestionDTO> questions;
    private ExamToBeTakenDTO dto;

    public ANTLRFormativeExamParser(Map<QuestionType, LinkedList<AbstractQuestionDTO>> questionsByType) {
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

        if (typeQuestions == null || typeQuestions.isEmpty())
            throw new NotEnoughQuestionsException(type);

        var question = System.currentTimeMillis() % 2 == 0
                ? typeQuestions.poll()
                : typeQuestions.pollLast();

        this.questions.add(question);
        return null;
    }
}
