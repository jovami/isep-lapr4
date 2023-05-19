// Generated from ExamSpec.g4 by ANTLR 4.12.0

package eapli.base.exam.domain.grammar;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExamSpecParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExamSpecVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#exam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExam(ExamSpecParser.ExamContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#title}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTitle(ExamSpecParser.TitleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeader(ExamSpecParser.HeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#description}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDescription(ExamSpecParser.DescriptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#feedback}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFeedback(ExamSpecParser.FeedbackContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#grading}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrading(ExamSpecParser.GradingContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#section}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSection(ExamSpecParser.SectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion(ExamSpecParser.QuestionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#matching}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatching(ExamSpecParser.MatchingContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#subquestion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubquestion(ExamSpecParser.SubquestionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#answer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnswer(ExamSpecParser.AnswerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#matching_solution}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatching_solution(ExamSpecParser.Matching_solutionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#match}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatch(ExamSpecParser.MatchContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#multiple_choice}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiple_choice(ExamSpecParser.Multiple_choiceContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#numerical_solution}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumerical_solution(ExamSpecParser.Numerical_solutionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#choice_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChoice_type(ExamSpecParser.Choice_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#short_answer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShort_answer(ExamSpecParser.Short_answerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#string_solution}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_solution(ExamSpecParser.String_solutionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#case_sensitive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCase_sensitive(ExamSpecParser.Case_sensitiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#numerical}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumerical(ExamSpecParser.NumericalContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#error}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitError(ExamSpecParser.ErrorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#missing_words}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMissing_words(ExamSpecParser.Missing_wordsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#group}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroup(ExamSpecParser.GroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#item}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItem(ExamSpecParser.ItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#choice}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChoice(ExamSpecParser.ChoiceContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#from_group}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFrom_group(ExamSpecParser.From_groupContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#true_false}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue_false(ExamSpecParser.True_falseContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamSpecParser#boolean_solution}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_solution(ExamSpecParser.Boolean_solutionContext ctx);
}