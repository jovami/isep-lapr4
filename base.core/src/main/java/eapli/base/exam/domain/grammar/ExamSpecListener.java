// Generated from ExamSpec.g4 by ANTLR 4.12.0
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExamSpecParser}.
 */
public interface ExamSpecListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#exam}.
	 * @param ctx the parse tree
	 */
	void enterExam(ExamSpecParser.ExamContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#exam}.
	 * @param ctx the parse tree
	 */
	void exitExam(ExamSpecParser.ExamContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#title}.
	 * @param ctx the parse tree
	 */
	void enterTitle(ExamSpecParser.TitleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#title}.
	 * @param ctx the parse tree
	 */
	void exitTitle(ExamSpecParser.TitleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#header}.
	 * @param ctx the parse tree
	 */
	void enterHeader(ExamSpecParser.HeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#header}.
	 * @param ctx the parse tree
	 */
	void exitHeader(ExamSpecParser.HeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#description}.
	 * @param ctx the parse tree
	 */
	void enterDescription(ExamSpecParser.DescriptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#description}.
	 * @param ctx the parse tree
	 */
	void exitDescription(ExamSpecParser.DescriptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#feedback}.
	 * @param ctx the parse tree
	 */
	void enterFeedback(ExamSpecParser.FeedbackContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#feedback}.
	 * @param ctx the parse tree
	 */
	void exitFeedback(ExamSpecParser.FeedbackContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#grading}.
	 * @param ctx the parse tree
	 */
	void enterGrading(ExamSpecParser.GradingContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#grading}.
	 * @param ctx the parse tree
	 */
	void exitGrading(ExamSpecParser.GradingContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#section}.
	 * @param ctx the parse tree
	 */
	void enterSection(ExamSpecParser.SectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#section}.
	 * @param ctx the parse tree
	 */
	void exitSection(ExamSpecParser.SectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#question}.
	 * @param ctx the parse tree
	 */
	void enterQuestion(ExamSpecParser.QuestionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#question}.
	 * @param ctx the parse tree
	 */
	void exitQuestion(ExamSpecParser.QuestionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#matching}.
	 * @param ctx the parse tree
	 */
	void enterMatching(ExamSpecParser.MatchingContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#matching}.
	 * @param ctx the parse tree
	 */
	void exitMatching(ExamSpecParser.MatchingContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#subquestion}.
	 * @param ctx the parse tree
	 */
	void enterSubquestion(ExamSpecParser.SubquestionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#subquestion}.
	 * @param ctx the parse tree
	 */
	void exitSubquestion(ExamSpecParser.SubquestionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#answer}.
	 * @param ctx the parse tree
	 */
	void enterAnswer(ExamSpecParser.AnswerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#answer}.
	 * @param ctx the parse tree
	 */
	void exitAnswer(ExamSpecParser.AnswerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#matching_solution}.
	 * @param ctx the parse tree
	 */
	void enterMatching_solution(ExamSpecParser.Matching_solutionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#matching_solution}.
	 * @param ctx the parse tree
	 */
	void exitMatching_solution(ExamSpecParser.Matching_solutionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#match}.
	 * @param ctx the parse tree
	 */
	void enterMatch(ExamSpecParser.MatchContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#match}.
	 * @param ctx the parse tree
	 */
	void exitMatch(ExamSpecParser.MatchContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#multiple_choice}.
	 * @param ctx the parse tree
	 */
	void enterMultiple_choice(ExamSpecParser.Multiple_choiceContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#multiple_choice}.
	 * @param ctx the parse tree
	 */
	void exitMultiple_choice(ExamSpecParser.Multiple_choiceContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#numerical_solution}.
	 * @param ctx the parse tree
	 */
	void enterNumerical_solution(ExamSpecParser.Numerical_solutionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#numerical_solution}.
	 * @param ctx the parse tree
	 */
	void exitNumerical_solution(ExamSpecParser.Numerical_solutionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#choice_type}.
	 * @param ctx the parse tree
	 */
	void enterChoice_type(ExamSpecParser.Choice_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#choice_type}.
	 * @param ctx the parse tree
	 */
	void exitChoice_type(ExamSpecParser.Choice_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#short_answer}.
	 * @param ctx the parse tree
	 */
	void enterShort_answer(ExamSpecParser.Short_answerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#short_answer}.
	 * @param ctx the parse tree
	 */
	void exitShort_answer(ExamSpecParser.Short_answerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#string_solution}.
	 * @param ctx the parse tree
	 */
	void enterString_solution(ExamSpecParser.String_solutionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#string_solution}.
	 * @param ctx the parse tree
	 */
	void exitString_solution(ExamSpecParser.String_solutionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#case_sensitive}.
	 * @param ctx the parse tree
	 */
	void enterCase_sensitive(ExamSpecParser.Case_sensitiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#case_sensitive}.
	 * @param ctx the parse tree
	 */
	void exitCase_sensitive(ExamSpecParser.Case_sensitiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#numerical}.
	 * @param ctx the parse tree
	 */
	void enterNumerical(ExamSpecParser.NumericalContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#numerical}.
	 * @param ctx the parse tree
	 */
	void exitNumerical(ExamSpecParser.NumericalContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#error}.
	 * @param ctx the parse tree
	 */
	void enterError(ExamSpecParser.ErrorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#error}.
	 * @param ctx the parse tree
	 */
	void exitError(ExamSpecParser.ErrorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#missing_words}.
	 * @param ctx the parse tree
	 */
	void enterMissing_words(ExamSpecParser.Missing_wordsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#missing_words}.
	 * @param ctx the parse tree
	 */
	void exitMissing_words(ExamSpecParser.Missing_wordsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#group}.
	 * @param ctx the parse tree
	 */
	void enterGroup(ExamSpecParser.GroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#group}.
	 * @param ctx the parse tree
	 */
	void exitGroup(ExamSpecParser.GroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#item}.
	 * @param ctx the parse tree
	 */
	void enterItem(ExamSpecParser.ItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#item}.
	 * @param ctx the parse tree
	 */
	void exitItem(ExamSpecParser.ItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#choice}.
	 * @param ctx the parse tree
	 */
	void enterChoice(ExamSpecParser.ChoiceContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#choice}.
	 * @param ctx the parse tree
	 */
	void exitChoice(ExamSpecParser.ChoiceContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#from_group}.
	 * @param ctx the parse tree
	 */
	void enterFrom_group(ExamSpecParser.From_groupContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#from_group}.
	 * @param ctx the parse tree
	 */
	void exitFrom_group(ExamSpecParser.From_groupContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#true_false}.
	 * @param ctx the parse tree
	 */
	void enterTrue_false(ExamSpecParser.True_falseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#true_false}.
	 * @param ctx the parse tree
	 */
	void exitTrue_false(ExamSpecParser.True_falseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamSpecParser#boolean_solution}.
	 * @param ctx the parse tree
	 */
	void enterBoolean_solution(ExamSpecParser.Boolean_solutionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamSpecParser#boolean_solution}.
	 * @param ctx the parse tree
	 */
	void exitBoolean_solution(ExamSpecParser.Boolean_solutionContext ctx);
}