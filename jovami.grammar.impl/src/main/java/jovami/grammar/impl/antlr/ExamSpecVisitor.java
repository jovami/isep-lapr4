package jovami.grammar.impl.antlr;

import eapli.base.exam.dto.ExamToBeTakenDTO;
import eapli.base.exam.dto.ExamToBeTakenDTO.Section;
import eapli.base.question.dto.AbstractQuestionDTO;
import eapli.base.question.dto.MatchingQuestion;
import eapli.base.question.dto.MissingWordsQuestion;
import eapli.base.question.dto.MultipleChoiceQuestion;
import eapli.base.question.dto.NumericalQuestion;
import eapli.base.question.dto.ShortAnswerQuestion;
import eapli.base.question.dto.TrueFalseQuestion;
import jovami.grammar.impl.antlr.exam.autogen.ExamSpecBaseVisitor;
import jovami.grammar.impl.antlr.exam.autogen.ExamSpecParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;

public class ExamSpecVisitor extends ExamSpecBaseVisitor<String> {
    private ExamToBeTakenDTO dto;
    private final List<Section> sections = new ArrayList<>();
    private List<AbstractQuestionDTO> questions;

    /**
     * Method that parses the given tree and returns an ExamToBeTakenDTO
     * This method should be called instead of the visit method
     *
     * @param tree the parse tree to be visited
     * @return the constructed ExamToBeTakenDTO
     */
    public ExamToBeTakenDTO dto(ParseTree tree) {
        visit(tree);
        return this.dto;
    }

    @Override
    public String visitExam(ExamSpecParser.ExamContext ctx) {
        var title = visitTitle(ctx.title());
        String description = "No description provided";

        var descriptionCtx = ctx.header().description();
        if (descriptionCtx != null)
            description = visitDescription(ctx.header().description());

        visitChildren(ctx);
        this.dto = new ExamToBeTakenDTO(title, description, sections);
        return null;
    }

    @Override
    public String visitTitle(ExamSpecParser.TitleContext ctx) {
        return ctx.value.getText().replaceAll("\"", "");
    }

    @Override
    public String visitDescription(ExamSpecParser.DescriptionContext ctx) {
        return ctx.value.getText().replaceAll("\"", "");
    }

    @Override
    public String visitSection(ExamSpecParser.SectionContext ctx) {
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
    public String visitMatching(ExamSpecParser.MatchingContext ctx) {
        List<String> phrase1 = new ArrayList<>();
        List<String> phrase2 = new ArrayList<>();

        for (var subquestionContext : ctx.subquestion()) {
            phrase1.add(visitSubquestion(subquestionContext));
        }

        for (var answerContext : ctx.answer()) {
            phrase2.add(visitAnswer(answerContext));
        }

        var description = visitDescription(ctx.description());

        this.questions.add(new MatchingQuestion(description, phrase1, phrase2));
        return null;
    }

    @Override
    public String visitSubquestion(ExamSpecParser.SubquestionContext ctx) {
        return ctx.value.getText().replaceAll("\"", "");
    }

    @Override
    public String visitAnswer(ExamSpecParser.AnswerContext ctx) {
        return ctx.value.getText().replaceAll("\"", "");
    }

    @Override
    public String visitMultiple_choice(ExamSpecParser.Multiple_choiceContext ctx) {
        List<String> options = new ArrayList<>();

        var singleAnswer = visitChoice_type(ctx.choice_type()).equals("single-answer");

        for (var answerContext : ctx.answer()) {
            options.add(visitAnswer(answerContext));
        }

        var description = visitDescription(ctx.description());

        this.questions.add(new MultipleChoiceQuestion(singleAnswer, description, options));
        return null;
    }

    @Override
    public String visitChoice_type(ExamSpecParser.Choice_typeContext ctx) {
        return ctx.value.getText();
    }

    @Override
    public String visitShort_answer(ExamSpecParser.Short_answerContext ctx) {
        var description = visitDescription(ctx.description());

        this.questions.add(new ShortAnswerQuestion(description));
        return null;
    }

    @Override
    public String visitNumerical(ExamSpecParser.NumericalContext ctx) {
        var description = visitDescription(ctx.description());

        this.questions.add(new NumericalQuestion(description));
        return null;
    }

    @Override
    public String visitMissing_words(ExamSpecParser.Missing_wordsContext ctx) {
        var description = visitDescription(ctx.description());
        var groups = new HashMap<String, List<String>>();
        var choices = new ArrayList<String>();

        for (var groupCtx : ctx.group()) {
            var id = visitGroup(groupCtx);
            var items = new ArrayList<String>();
            for (var itemCtx : groupCtx.item()){
                items.add(visitItem(itemCtx));
            }
            groups.put(id, items);
        }

        var choiceContexts = new ArrayList<>(ctx.choice());
        choiceContexts.sort(Comparator.comparing(choiceCtx -> Integer.parseInt(choiceCtx.id.getText())));

        for (var choiceCtx : choiceContexts) {
            choices.add(visitChoice(choiceCtx));
        }

        this.questions.add(new MissingWordsQuestion(description, groups, choices));
        return null;
    }

    @Override
    public String visitGroup(ExamSpecParser.GroupContext ctx) {
        return ctx.id.getText().replaceAll("\"", "");
    }

    @Override
    public String visitItem(ExamSpecParser.ItemContext ctx) {
        return ctx.value.getText().replaceAll("\"", "");
    }

    @Override
    public String visitChoice(ExamSpecParser.ChoiceContext ctx) {
        return ctx.from_group().id.getText().replaceAll("\"", "");
    }

    @Override
    public String visitTrue_false(ExamSpecParser.True_falseContext ctx) {
        var description = visitDescription(ctx.description());

        this.questions.add(new TrueFalseQuestion(description));
        return null;
    }
}
