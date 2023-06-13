package jovami.grammar.impl.antlr;

import eapli.base.question.dto.AbstractQuestionDTO;
import eapli.base.question.dto.MatchingQuestion;
import eapli.base.question.dto.MissingWordsQuestion;
import eapli.base.question.dto.MultipleChoiceQuestion;
import eapli.base.question.dto.NumericalQuestion;
import eapli.base.question.dto.ShortAnswerQuestion;
import eapli.base.question.dto.TrueFalseQuestion;
import jovami.grammar.impl.antlr.question.autogen.QuestionParser.*;
import jovami.grammar.impl.antlr.question.autogen.QuestionBaseVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

final class ANTLRQuestionParser extends QuestionBaseVisitor<String> {
    private final Long id;
    private AbstractQuestionDTO question;

    public ANTLRQuestionParser(Long id) {
        this.id = id;
    }

    public AbstractQuestionDTO question(ParseTree tree) {
        visit(tree);
        return this.question;
    }

    @Override
    public String visitQuestion(QuestionContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override
    public String visitDescription(DescriptionContext ctx) {
        return ctx.value.getText().replaceAll("\"", "");
    }

    @Override
    public String visitMatching(MatchingContext ctx) {
        List<String> phrase1 = new ArrayList<>();
        List<String> phrase2 = new ArrayList<>();

        for (var subquestionContext : ctx.subquestion()) {
            phrase1.add(visitSubquestion(subquestionContext));
        }

        for (var answerContext : ctx.answer()) {
            phrase2.add(visitAnswer(answerContext));
        }

        var description = visitDescription(ctx.description());

        this.question = new MatchingQuestion(this.id, description, phrase1, phrase2);
        return null;
    }

    @Override
    public String visitSubquestion(SubquestionContext ctx) {
        return ctx.value.getText().replaceAll("\"", "");
    }

    @Override
    public String visitAnswer(AnswerContext ctx) {
        return ctx.value.getText().replaceAll("\"", "");
    }

    @Override
    public String visitMultiple_choice(Multiple_choiceContext ctx) {
        List<String> options = new ArrayList<>();

        var singleAnswer = visitChoice_type(ctx.choice_type()).equals("single-answer");

        for (var answerContext : ctx.answer()) {
            options.add(visitAnswer(answerContext));
        }

        var description = visitDescription(ctx.description());

        this.question = new MultipleChoiceQuestion(this.id, singleAnswer, description, options);
        return null;
    }

    @Override
    public String visitChoice_type(Choice_typeContext ctx) {
        return ctx.value.getText();
    }

    @Override
    public String visitShort_answer(Short_answerContext ctx) {
        var description = visitDescription(ctx.description());

        this.question = new ShortAnswerQuestion(this.id, description);
        return null;
    }

    @Override
    public String visitNumerical(NumericalContext ctx) {
        var description = visitDescription(ctx.description());

        this.question = new NumericalQuestion(this.id, description);
        return null;
    }

    @Override
    public String visitMissing_words(Missing_wordsContext ctx) {
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

        this.question = new MissingWordsQuestion(this.id, description, groups, choices);
        return null;
    }

    @Override
    public String visitGroup(GroupContext ctx) {
        return ctx.id.getText().replaceAll("\"", "");
    }

    @Override
    public String visitItem(ItemContext ctx) {
        return ctx.value.getText().replaceAll("\"", "");
    }

    @Override
    public String visitChoice(ChoiceContext ctx) {
        return ctx.from_group().id.getText().replaceAll("\"", "");
    }

    @Override
    public String visitTrue_false(True_falseContext ctx) {
        var description = visitDescription(ctx.description());

        this.question = new TrueFalseQuestion(this.id, description);
        return null;
    }
}
