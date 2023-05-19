// Generated from ExamSpec.g4 by ANTLR 4.12.0

package eapli.base.exam.domain.grammar;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ExamSpecParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, COLON=30, LEFT_BRACE=31, 
		RIGHT_BRACE=32, LEFT_BRACKET=33, RIGHT_BRACKET=34, DASH=35, COMMENT=36, 
		FLOAT=37, INT=38, BOOL=39, STRING=40, WS=41;
	public static final int
		RULE_exam = 0, RULE_title = 1, RULE_header = 2, RULE_description = 3, 
		RULE_feedback = 4, RULE_grading = 5, RULE_section = 6, RULE_question = 7, 
		RULE_matching = 8, RULE_subquestion = 9, RULE_answer = 10, RULE_matching_solution = 11, 
		RULE_match = 12, RULE_multiple_choice = 13, RULE_numerical_solution = 14, 
		RULE_choice_type = 15, RULE_short_answer = 16, RULE_string_solution = 17, 
		RULE_case_sensitive = 18, RULE_numerical = 19, RULE_error = 20, RULE_missing_words = 21, 
		RULE_group = 22, RULE_item = 23, RULE_choice = 24, RULE_from_group = 25, 
		RULE_true_false = 26, RULE_boolean_solution = 27;
	private static String[] makeRuleNames() {
		return new String[] {
			"exam", "title", "header", "description", "feedback", "grading", "section", 
			"question", "matching", "subquestion", "answer", "matching_solution", 
			"match", "multiple_choice", "numerical_solution", "choice_type", "short_answer", 
			"string_solution", "case_sensitive", "numerical", "error", "missing_words", 
			"group", "item", "choice", "from_group", "true_false", "boolean_solution"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'EXAM'", "'TITLE'", "'HEADER'", "'DESCRIPTION'", "'FEEDBACK'", 
			"'none'", "'on-submission'", "'after-closing'", "'GRADING'", "'SECTION'", 
			"'MATCHING'", "'SUBQUESTION'", "'ANSWER'", "'SOLUTION'", "'MULTIPLE_CHOICE'", 
			"','", "'CHOICE_TYPE'", "'single-answer'", "'multiple-answer'", "'SHORT_ANSWER'", 
			"'CASE_SENSITIVE:'", "'NUMERICAL'", "'ERROR'", "'MISSING_WORDS'", "'GROUP'", 
			"'ITEM'", "'CHOICE'", "'FROM_GROUP'", "'TRUE_FALSE'", "':'", "'{'", "'}'", 
			"'['", "']'", "'-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "COLON", "LEFT_BRACE", "RIGHT_BRACE", 
			"LEFT_BRACKET", "RIGHT_BRACKET", "DASH", "COMMENT", "FLOAT", "INT", "BOOL", 
			"STRING", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ExamSpec.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExamSpecParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExamContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(ExamSpecParser.LEFT_BRACE, 0); }
		public TitleContext title() {
			return getRuleContext(TitleContext.class,0);
		}
		public HeaderContext header() {
			return getRuleContext(HeaderContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(ExamSpecParser.RIGHT_BRACE, 0); }
		public List<SectionContext> section() {
			return getRuleContexts(SectionContext.class);
		}
		public SectionContext section(int i) {
			return getRuleContext(SectionContext.class,i);
		}
		public ExamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exam; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitExam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExamContext exam() throws RecognitionException {
		ExamContext _localctx = new ExamContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_exam);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(T__0);
			setState(57);
			match(LEFT_BRACE);
			setState(58);
			title();
			setState(59);
			header();
			setState(61); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(60);
				section();
				}
				}
				setState(63); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__9 );
			setState(65);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TitleContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public TerminalNode STRING() { return getToken(ExamSpecParser.STRING, 0); }
		public TitleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_title; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitTitle(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TitleContext title() throws RecognitionException {
		TitleContext _localctx = new TitleContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_title);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			match(T__1);
			setState(68);
			match(COLON);
			setState(69);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HeaderContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(ExamSpecParser.LEFT_BRACE, 0); }
		public FeedbackContext feedback() {
			return getRuleContext(FeedbackContext.class,0);
		}
		public GradingContext grading() {
			return getRuleContext(GradingContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(ExamSpecParser.RIGHT_BRACE, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public HeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_header; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeaderContext header() throws RecognitionException {
		HeaderContext _localctx = new HeaderContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_header);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(T__2);
			setState(72);
			match(LEFT_BRACE);
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(73);
				description();
				}
			}

			setState(76);
			feedback();
			setState(77);
			grading();
			setState(78);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DescriptionContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public TerminalNode STRING() { return getToken(ExamSpecParser.STRING, 0); }
		public DescriptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_description; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitDescription(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DescriptionContext description() throws RecognitionException {
		DescriptionContext _localctx = new DescriptionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_description);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(T__3);
			setState(81);
			match(COLON);
			setState(82);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FeedbackContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public FeedbackContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_feedback; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitFeedback(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FeedbackContext feedback() throws RecognitionException {
		FeedbackContext _localctx = new FeedbackContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_feedback);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			match(T__4);
			setState(85);
			match(COLON);
			setState(86);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 448L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GradingContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public GradingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grading; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitGrading(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GradingContext grading() throws RecognitionException {
		GradingContext _localctx = new GradingContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_grading);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(T__8);
			setState(89);
			match(COLON);
			setState(90);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 448L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SectionContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ExamSpecParser.INT, 0); }
		public TerminalNode LEFT_BRACE() { return getToken(ExamSpecParser.LEFT_BRACE, 0); }
		public TerminalNode RIGHT_BRACE() { return getToken(ExamSpecParser.RIGHT_BRACE, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public List<QuestionContext> question() {
			return getRuleContexts(QuestionContext.class);
		}
		public QuestionContext question(int i) {
			return getRuleContext(QuestionContext.class,i);
		}
		public SectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_section; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitSection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SectionContext section() throws RecognitionException {
		SectionContext _localctx = new SectionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_section);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			match(T__9);
			setState(93);
			match(INT);
			setState(94);
			match(LEFT_BRACE);
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(95);
				description();
				}
			}

			setState(99); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(98);
				question();
				}
				}
				setState(101); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 558925824L) != 0) );
			setState(103);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QuestionContext extends ParserRuleContext {
		public MatchingContext matching() {
			return getRuleContext(MatchingContext.class,0);
		}
		public Multiple_choiceContext multiple_choice() {
			return getRuleContext(Multiple_choiceContext.class,0);
		}
		public Short_answerContext short_answer() {
			return getRuleContext(Short_answerContext.class,0);
		}
		public NumericalContext numerical() {
			return getRuleContext(NumericalContext.class,0);
		}
		public Missing_wordsContext missing_words() {
			return getRuleContext(Missing_wordsContext.class,0);
		}
		public True_falseContext true_false() {
			return getRuleContext(True_falseContext.class,0);
		}
		public QuestionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitQuestion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuestionContext question() throws RecognitionException {
		QuestionContext _localctx = new QuestionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_question);
		try {
			setState(111);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__10:
				enterOuterAlt(_localctx, 1);
				{
				setState(105);
				matching();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(106);
				multiple_choice();
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 3);
				{
				setState(107);
				short_answer();
				}
				break;
			case T__21:
				enterOuterAlt(_localctx, 4);
				{
				setState(108);
				numerical();
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 5);
				{
				setState(109);
				missing_words();
				}
				break;
			case T__28:
				enterOuterAlt(_localctx, 6);
				{
				setState(110);
				true_false();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MatchingContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(ExamSpecParser.LEFT_BRACE, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(ExamSpecParser.RIGHT_BRACE, 0); }
		public List<SubquestionContext> subquestion() {
			return getRuleContexts(SubquestionContext.class);
		}
		public SubquestionContext subquestion(int i) {
			return getRuleContext(SubquestionContext.class,i);
		}
		public List<AnswerContext> answer() {
			return getRuleContexts(AnswerContext.class);
		}
		public AnswerContext answer(int i) {
			return getRuleContext(AnswerContext.class,i);
		}
		public List<Matching_solutionContext> matching_solution() {
			return getRuleContexts(Matching_solutionContext.class);
		}
		public Matching_solutionContext matching_solution(int i) {
			return getRuleContext(Matching_solutionContext.class,i);
		}
		public MatchingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_matching; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitMatching(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MatchingContext matching() throws RecognitionException {
		MatchingContext _localctx = new MatchingContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_matching);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(T__10);
			setState(114);
			match(LEFT_BRACE);
			setState(115);
			description();
			setState(117); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(116);
				subquestion();
				}
				}
				setState(119); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__11 );
			setState(122); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(121);
				answer();
				}
				}
				setState(124); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__12 );
			setState(127); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(126);
				matching_solution();
				}
				}
				setState(129); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__13 );
			setState(131);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SubquestionContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ExamSpecParser.INT, 0); }
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public TerminalNode STRING() { return getToken(ExamSpecParser.STRING, 0); }
		public SubquestionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subquestion; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitSubquestion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubquestionContext subquestion() throws RecognitionException {
		SubquestionContext _localctx = new SubquestionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_subquestion);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(T__11);
			setState(134);
			match(INT);
			setState(135);
			match(COLON);
			setState(136);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnswerContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ExamSpecParser.INT, 0); }
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public TerminalNode STRING() { return getToken(ExamSpecParser.STRING, 0); }
		public AnswerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_answer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitAnswer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnswerContext answer() throws RecognitionException {
		AnswerContext _localctx = new AnswerContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_answer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(T__12);
			setState(139);
			match(INT);
			setState(140);
			match(COLON);
			setState(141);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Matching_solutionContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ExamSpecParser.INT, 0); }
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public MatchContext match() {
			return getRuleContext(MatchContext.class,0);
		}
		public TerminalNode LEFT_BRACKET() { return getToken(ExamSpecParser.LEFT_BRACKET, 0); }
		public TerminalNode FLOAT() { return getToken(ExamSpecParser.FLOAT, 0); }
		public TerminalNode RIGHT_BRACKET() { return getToken(ExamSpecParser.RIGHT_BRACKET, 0); }
		public Matching_solutionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_matching_solution; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitMatching_solution(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Matching_solutionContext matching_solution() throws RecognitionException {
		Matching_solutionContext _localctx = new Matching_solutionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_matching_solution);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			match(T__13);
			setState(144);
			match(INT);
			setState(145);
			match(COLON);
			setState(146);
			match();
			setState(147);
			match(LEFT_BRACKET);
			setState(148);
			match(FLOAT);
			setState(149);
			match(RIGHT_BRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MatchContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(ExamSpecParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(ExamSpecParser.INT, i);
		}
		public TerminalNode DASH() { return getToken(ExamSpecParser.DASH, 0); }
		public MatchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_match; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitMatch(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MatchContext match() throws RecognitionException {
		MatchContext _localctx = new MatchContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_match);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			match(INT);
			setState(152);
			match(DASH);
			setState(153);
			match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Multiple_choiceContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(ExamSpecParser.LEFT_BRACE, 0); }
		public Choice_typeContext choice_type() {
			return getRuleContext(Choice_typeContext.class,0);
		}
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(ExamSpecParser.RIGHT_BRACE, 0); }
		public List<AnswerContext> answer() {
			return getRuleContexts(AnswerContext.class);
		}
		public AnswerContext answer(int i) {
			return getRuleContext(AnswerContext.class,i);
		}
		public List<Numerical_solutionContext> numerical_solution() {
			return getRuleContexts(Numerical_solutionContext.class);
		}
		public Numerical_solutionContext numerical_solution(int i) {
			return getRuleContext(Numerical_solutionContext.class,i);
		}
		public Multiple_choiceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiple_choice; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitMultiple_choice(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multiple_choiceContext multiple_choice() throws RecognitionException {
		Multiple_choiceContext _localctx = new Multiple_choiceContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_multiple_choice);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			match(T__14);
			setState(156);
			match(LEFT_BRACE);
			setState(157);
			choice_type();
			setState(158);
			description();
			setState(160); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(159);
				answer();
				}
				}
				setState(162); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__12 );
			setState(165); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(164);
				numerical_solution();
				}
				}
				setState(167); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__13 );
			setState(169);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Numerical_solutionContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(ExamSpecParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(ExamSpecParser.INT, i);
		}
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public TerminalNode LEFT_BRACKET() { return getToken(ExamSpecParser.LEFT_BRACKET, 0); }
		public TerminalNode FLOAT() { return getToken(ExamSpecParser.FLOAT, 0); }
		public TerminalNode RIGHT_BRACKET() { return getToken(ExamSpecParser.RIGHT_BRACKET, 0); }
		public Numerical_solutionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numerical_solution; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitNumerical_solution(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Numerical_solutionContext numerical_solution() throws RecognitionException {
		Numerical_solutionContext _localctx = new Numerical_solutionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_numerical_solution);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			match(T__13);
			setState(172);
			match(INT);
			setState(173);
			match(COLON);
			setState(174);
			match(INT);
			setState(179);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__15) {
				{
				{
				setState(175);
				match(T__15);
				setState(176);
				match(INT);
				}
				}
				setState(181);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(182);
			match(LEFT_BRACKET);
			setState(183);
			match(FLOAT);
			setState(184);
			match(RIGHT_BRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Choice_typeContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public Choice_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_choice_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitChoice_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Choice_typeContext choice_type() throws RecognitionException {
		Choice_typeContext _localctx = new Choice_typeContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_choice_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			match(T__16);
			setState(187);
			match(COLON);
			setState(188);
			_la = _input.LA(1);
			if ( !(_la==T__17 || _la==T__18) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Short_answerContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(ExamSpecParser.LEFT_BRACE, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public Case_sensitiveContext case_sensitive() {
			return getRuleContext(Case_sensitiveContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(ExamSpecParser.RIGHT_BRACE, 0); }
		public List<String_solutionContext> string_solution() {
			return getRuleContexts(String_solutionContext.class);
		}
		public String_solutionContext string_solution(int i) {
			return getRuleContext(String_solutionContext.class,i);
		}
		public Short_answerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_short_answer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitShort_answer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Short_answerContext short_answer() throws RecognitionException {
		Short_answerContext _localctx = new Short_answerContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_short_answer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(T__19);
			setState(191);
			match(LEFT_BRACE);
			setState(192);
			description();
			setState(193);
			case_sensitive();
			setState(195); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(194);
				string_solution();
				}
				}
				setState(197); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__13 );
			setState(199);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class String_solutionContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ExamSpecParser.INT, 0); }
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public TerminalNode STRING() { return getToken(ExamSpecParser.STRING, 0); }
		public TerminalNode LEFT_BRACKET() { return getToken(ExamSpecParser.LEFT_BRACKET, 0); }
		public TerminalNode FLOAT() { return getToken(ExamSpecParser.FLOAT, 0); }
		public TerminalNode RIGHT_BRACKET() { return getToken(ExamSpecParser.RIGHT_BRACKET, 0); }
		public String_solutionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string_solution; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitString_solution(this);
			else return visitor.visitChildren(this);
		}
	}

	public final String_solutionContext string_solution() throws RecognitionException {
		String_solutionContext _localctx = new String_solutionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_string_solution);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(T__13);
			setState(202);
			match(INT);
			setState(203);
			match(COLON);
			setState(204);
			match(STRING);
			setState(205);
			match(LEFT_BRACKET);
			setState(206);
			match(FLOAT);
			setState(207);
			match(RIGHT_BRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Case_sensitiveContext extends ParserRuleContext {
		public TerminalNode BOOL() { return getToken(ExamSpecParser.BOOL, 0); }
		public Case_sensitiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_case_sensitive; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitCase_sensitive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Case_sensitiveContext case_sensitive() throws RecognitionException {
		Case_sensitiveContext _localctx = new Case_sensitiveContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_case_sensitive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(T__20);
			setState(210);
			match(BOOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NumericalContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(ExamSpecParser.LEFT_BRACE, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(ExamSpecParser.RIGHT_BRACE, 0); }
		public ErrorContext error() {
			return getRuleContext(ErrorContext.class,0);
		}
		public List<Numerical_solutionContext> numerical_solution() {
			return getRuleContexts(Numerical_solutionContext.class);
		}
		public Numerical_solutionContext numerical_solution(int i) {
			return getRuleContext(Numerical_solutionContext.class,i);
		}
		public NumericalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numerical; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitNumerical(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericalContext numerical() throws RecognitionException {
		NumericalContext _localctx = new NumericalContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_numerical);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			match(T__21);
			setState(213);
			match(LEFT_BRACE);
			setState(214);
			description();
			setState(216);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__22) {
				{
				setState(215);
				error();
				}
			}

			setState(219); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(218);
				numerical_solution();
				}
				}
				setState(221); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__13 );
			setState(223);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ErrorContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public TerminalNode FLOAT() { return getToken(ExamSpecParser.FLOAT, 0); }
		public ErrorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_error; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitError(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ErrorContext error() throws RecognitionException {
		ErrorContext _localctx = new ErrorContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_error);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			match(T__22);
			setState(226);
			match(COLON);
			setState(227);
			match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Missing_wordsContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(ExamSpecParser.LEFT_BRACE, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(ExamSpecParser.RIGHT_BRACE, 0); }
		public List<GroupContext> group() {
			return getRuleContexts(GroupContext.class);
		}
		public GroupContext group(int i) {
			return getRuleContext(GroupContext.class,i);
		}
		public List<ChoiceContext> choice() {
			return getRuleContexts(ChoiceContext.class);
		}
		public ChoiceContext choice(int i) {
			return getRuleContext(ChoiceContext.class,i);
		}
		public Missing_wordsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_missing_words; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitMissing_words(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Missing_wordsContext missing_words() throws RecognitionException {
		Missing_wordsContext _localctx = new Missing_wordsContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_missing_words);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			match(T__23);
			setState(230);
			match(LEFT_BRACE);
			setState(231);
			description();
			setState(233); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(232);
				group();
				}
				}
				setState(235); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__24 );
			setState(238); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(237);
				choice();
				}
				}
				setState(240); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__26 );
			setState(242);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GroupContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(ExamSpecParser.LEFT_BRACE, 0); }
		public TerminalNode RIGHT_BRACE() { return getToken(ExamSpecParser.RIGHT_BRACE, 0); }
		public TerminalNode STRING() { return getToken(ExamSpecParser.STRING, 0); }
		public TerminalNode INT() { return getToken(ExamSpecParser.INT, 0); }
		public List<ItemContext> item() {
			return getRuleContexts(ItemContext.class);
		}
		public ItemContext item(int i) {
			return getRuleContext(ItemContext.class,i);
		}
		public GroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_group; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitGroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupContext group() throws RecognitionException {
		GroupContext _localctx = new GroupContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_group);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			match(T__24);
			setState(245);
			_la = _input.LA(1);
			if ( !(_la==INT || _la==STRING) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(246);
			match(LEFT_BRACE);
			setState(248); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(247);
				item();
				}
				}
				setState(250); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__25 );
			setState(252);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ItemContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ExamSpecParser.INT, 0); }
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public TerminalNode STRING() { return getToken(ExamSpecParser.STRING, 0); }
		public ItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_item; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ItemContext item() throws RecognitionException {
		ItemContext _localctx = new ItemContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_item);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			match(T__25);
			setState(255);
			match(INT);
			setState(256);
			match(COLON);
			setState(257);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ChoiceContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ExamSpecParser.INT, 0); }
		public TerminalNode LEFT_BRACE() { return getToken(ExamSpecParser.LEFT_BRACE, 0); }
		public From_groupContext from_group() {
			return getRuleContext(From_groupContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(ExamSpecParser.RIGHT_BRACE, 0); }
		public List<Numerical_solutionContext> numerical_solution() {
			return getRuleContexts(Numerical_solutionContext.class);
		}
		public Numerical_solutionContext numerical_solution(int i) {
			return getRuleContext(Numerical_solutionContext.class,i);
		}
		public ChoiceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_choice; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitChoice(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChoiceContext choice() throws RecognitionException {
		ChoiceContext _localctx = new ChoiceContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_choice);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			match(T__26);
			setState(260);
			match(INT);
			setState(261);
			match(LEFT_BRACE);
			setState(263); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(262);
				numerical_solution();
				}
				}
				setState(265); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__13 );
			setState(267);
			from_group();
			setState(268);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class From_groupContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public TerminalNode STRING() { return getToken(ExamSpecParser.STRING, 0); }
		public TerminalNode INT() { return getToken(ExamSpecParser.INT, 0); }
		public From_groupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_from_group; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitFrom_group(this);
			else return visitor.visitChildren(this);
		}
	}

	public final From_groupContext from_group() throws RecognitionException {
		From_groupContext _localctx = new From_groupContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_from_group);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			match(T__27);
			setState(271);
			match(COLON);
			setState(272);
			_la = _input.LA(1);
			if ( !(_la==INT || _la==STRING) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class True_falseContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(ExamSpecParser.LEFT_BRACE, 0); }
		public DescriptionContext description() {
			return getRuleContext(DescriptionContext.class,0);
		}
		public Boolean_solutionContext boolean_solution() {
			return getRuleContext(Boolean_solutionContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(ExamSpecParser.RIGHT_BRACE, 0); }
		public True_falseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_true_false; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitTrue_false(this);
			else return visitor.visitChildren(this);
		}
	}

	public final True_falseContext true_false() throws RecognitionException {
		True_falseContext _localctx = new True_falseContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_true_false);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(274);
			match(T__28);
			setState(275);
			match(LEFT_BRACE);
			setState(276);
			description();
			setState(277);
			boolean_solution();
			setState(278);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Boolean_solutionContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ExamSpecParser.INT, 0); }
		public TerminalNode COLON() { return getToken(ExamSpecParser.COLON, 0); }
		public TerminalNode BOOL() { return getToken(ExamSpecParser.BOOL, 0); }
		public TerminalNode LEFT_BRACKET() { return getToken(ExamSpecParser.LEFT_BRACKET, 0); }
		public TerminalNode FLOAT() { return getToken(ExamSpecParser.FLOAT, 0); }
		public TerminalNode RIGHT_BRACKET() { return getToken(ExamSpecParser.RIGHT_BRACKET, 0); }
		public Boolean_solutionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolean_solution; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamSpecVisitor ) return ((ExamSpecVisitor<? extends T>)visitor).visitBoolean_solution(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Boolean_solutionContext boolean_solution() throws RecognitionException {
		Boolean_solutionContext _localctx = new Boolean_solutionContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_boolean_solution);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			match(T__13);
			setState(281);
			match(INT);
			setState(282);
			match(COLON);
			setState(283);
			match(BOOL);
			setState(284);
			match(LEFT_BRACKET);
			setState(285);
			match(FLOAT);
			setState(286);
			match(RIGHT_BRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001)\u0121\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0004\u0000"+
		">\b\u0000\u000b\u0000\f\u0000?\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0003"+
		"\u0002K\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006a\b\u0006\u0001"+
		"\u0006\u0004\u0006d\b\u0006\u000b\u0006\f\u0006e\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0003\u0007p\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0004\bv\b\b\u000b"+
		"\b\f\bw\u0001\b\u0004\b{\b\b\u000b\b\f\b|\u0001\b\u0004\b\u0080\b\b\u000b"+
		"\b\f\b\u0081\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0004\r\u00a1"+
		"\b\r\u000b\r\f\r\u00a2\u0001\r\u0004\r\u00a6\b\r\u000b\r\f\r\u00a7\u0001"+
		"\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0005\u000e\u00b2\b\u000e\n\u000e\f\u000e\u00b5\t\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0004\u0010\u00c4\b\u0010\u000b\u0010\f\u0010\u00c5\u0001\u0010"+
		"\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u00d9\b\u0013"+
		"\u0001\u0013\u0004\u0013\u00dc\b\u0013\u000b\u0013\f\u0013\u00dd\u0001"+
		"\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0004\u0015\u00ea\b\u0015\u000b"+
		"\u0015\f\u0015\u00eb\u0001\u0015\u0004\u0015\u00ef\b\u0015\u000b\u0015"+
		"\f\u0015\u00f0\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0004\u0016\u00f9\b\u0016\u000b\u0016\f\u0016\u00fa\u0001"+
		"\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0004\u0018\u0108"+
		"\b\u0018\u000b\u0018\f\u0018\u0109\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0000\u0000\u001c\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010"+
		"\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.0246\u0000\u0003\u0001"+
		"\u0000\u0006\b\u0001\u0000\u0012\u0013\u0002\u0000&&((\u011a\u00008\u0001"+
		"\u0000\u0000\u0000\u0002C\u0001\u0000\u0000\u0000\u0004G\u0001\u0000\u0000"+
		"\u0000\u0006P\u0001\u0000\u0000\u0000\bT\u0001\u0000\u0000\u0000\nX\u0001"+
		"\u0000\u0000\u0000\f\\\u0001\u0000\u0000\u0000\u000eo\u0001\u0000\u0000"+
		"\u0000\u0010q\u0001\u0000\u0000\u0000\u0012\u0085\u0001\u0000\u0000\u0000"+
		"\u0014\u008a\u0001\u0000\u0000\u0000\u0016\u008f\u0001\u0000\u0000\u0000"+
		"\u0018\u0097\u0001\u0000\u0000\u0000\u001a\u009b\u0001\u0000\u0000\u0000"+
		"\u001c\u00ab\u0001\u0000\u0000\u0000\u001e\u00ba\u0001\u0000\u0000\u0000"+
		" \u00be\u0001\u0000\u0000\u0000\"\u00c9\u0001\u0000\u0000\u0000$\u00d1"+
		"\u0001\u0000\u0000\u0000&\u00d4\u0001\u0000\u0000\u0000(\u00e1\u0001\u0000"+
		"\u0000\u0000*\u00e5\u0001\u0000\u0000\u0000,\u00f4\u0001\u0000\u0000\u0000"+
		".\u00fe\u0001\u0000\u0000\u00000\u0103\u0001\u0000\u0000\u00002\u010e"+
		"\u0001\u0000\u0000\u00004\u0112\u0001\u0000\u0000\u00006\u0118\u0001\u0000"+
		"\u0000\u000089\u0005\u0001\u0000\u00009:\u0005\u001f\u0000\u0000:;\u0003"+
		"\u0002\u0001\u0000;=\u0003\u0004\u0002\u0000<>\u0003\f\u0006\u0000=<\u0001"+
		"\u0000\u0000\u0000>?\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000\u0000"+
		"?@\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000AB\u0005 \u0000\u0000"+
		"B\u0001\u0001\u0000\u0000\u0000CD\u0005\u0002\u0000\u0000DE\u0005\u001e"+
		"\u0000\u0000EF\u0005(\u0000\u0000F\u0003\u0001\u0000\u0000\u0000GH\u0005"+
		"\u0003\u0000\u0000HJ\u0005\u001f\u0000\u0000IK\u0003\u0006\u0003\u0000"+
		"JI\u0001\u0000\u0000\u0000JK\u0001\u0000\u0000\u0000KL\u0001\u0000\u0000"+
		"\u0000LM\u0003\b\u0004\u0000MN\u0003\n\u0005\u0000NO\u0005 \u0000\u0000"+
		"O\u0005\u0001\u0000\u0000\u0000PQ\u0005\u0004\u0000\u0000QR\u0005\u001e"+
		"\u0000\u0000RS\u0005(\u0000\u0000S\u0007\u0001\u0000\u0000\u0000TU\u0005"+
		"\u0005\u0000\u0000UV\u0005\u001e\u0000\u0000VW\u0007\u0000\u0000\u0000"+
		"W\t\u0001\u0000\u0000\u0000XY\u0005\t\u0000\u0000YZ\u0005\u001e\u0000"+
		"\u0000Z[\u0007\u0000\u0000\u0000[\u000b\u0001\u0000\u0000\u0000\\]\u0005"+
		"\n\u0000\u0000]^\u0005&\u0000\u0000^`\u0005\u001f\u0000\u0000_a\u0003"+
		"\u0006\u0003\u0000`_\u0001\u0000\u0000\u0000`a\u0001\u0000\u0000\u0000"+
		"ac\u0001\u0000\u0000\u0000bd\u0003\u000e\u0007\u0000cb\u0001\u0000\u0000"+
		"\u0000de\u0001\u0000\u0000\u0000ec\u0001\u0000\u0000\u0000ef\u0001\u0000"+
		"\u0000\u0000fg\u0001\u0000\u0000\u0000gh\u0005 \u0000\u0000h\r\u0001\u0000"+
		"\u0000\u0000ip\u0003\u0010\b\u0000jp\u0003\u001a\r\u0000kp\u0003 \u0010"+
		"\u0000lp\u0003&\u0013\u0000mp\u0003*\u0015\u0000np\u00034\u001a\u0000"+
		"oi\u0001\u0000\u0000\u0000oj\u0001\u0000\u0000\u0000ok\u0001\u0000\u0000"+
		"\u0000ol\u0001\u0000\u0000\u0000om\u0001\u0000\u0000\u0000on\u0001\u0000"+
		"\u0000\u0000p\u000f\u0001\u0000\u0000\u0000qr\u0005\u000b\u0000\u0000"+
		"rs\u0005\u001f\u0000\u0000su\u0003\u0006\u0003\u0000tv\u0003\u0012\t\u0000"+
		"ut\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000\u0000wu\u0001\u0000\u0000"+
		"\u0000wx\u0001\u0000\u0000\u0000xz\u0001\u0000\u0000\u0000y{\u0003\u0014"+
		"\n\u0000zy\u0001\u0000\u0000\u0000{|\u0001\u0000\u0000\u0000|z\u0001\u0000"+
		"\u0000\u0000|}\u0001\u0000\u0000\u0000}\u007f\u0001\u0000\u0000\u0000"+
		"~\u0080\u0003\u0016\u000b\u0000\u007f~\u0001\u0000\u0000\u0000\u0080\u0081"+
		"\u0001\u0000\u0000\u0000\u0081\u007f\u0001\u0000\u0000\u0000\u0081\u0082"+
		"\u0001\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0084"+
		"\u0005 \u0000\u0000\u0084\u0011\u0001\u0000\u0000\u0000\u0085\u0086\u0005"+
		"\f\u0000\u0000\u0086\u0087\u0005&\u0000\u0000\u0087\u0088\u0005\u001e"+
		"\u0000\u0000\u0088\u0089\u0005(\u0000\u0000\u0089\u0013\u0001\u0000\u0000"+
		"\u0000\u008a\u008b\u0005\r\u0000\u0000\u008b\u008c\u0005&\u0000\u0000"+
		"\u008c\u008d\u0005\u001e\u0000\u0000\u008d\u008e\u0005(\u0000\u0000\u008e"+
		"\u0015\u0001\u0000\u0000\u0000\u008f\u0090\u0005\u000e\u0000\u0000\u0090"+
		"\u0091\u0005&\u0000\u0000\u0091\u0092\u0005\u001e\u0000\u0000\u0092\u0093"+
		"\u0003\u0018\f\u0000\u0093\u0094\u0005!\u0000\u0000\u0094\u0095\u0005"+
		"%\u0000\u0000\u0095\u0096\u0005\"\u0000\u0000\u0096\u0017\u0001\u0000"+
		"\u0000\u0000\u0097\u0098\u0005&\u0000\u0000\u0098\u0099\u0005#\u0000\u0000"+
		"\u0099\u009a\u0005&\u0000\u0000\u009a\u0019\u0001\u0000\u0000\u0000\u009b"+
		"\u009c\u0005\u000f\u0000\u0000\u009c\u009d\u0005\u001f\u0000\u0000\u009d"+
		"\u009e\u0003\u001e\u000f\u0000\u009e\u00a0\u0003\u0006\u0003\u0000\u009f"+
		"\u00a1\u0003\u0014\n\u0000\u00a0\u009f\u0001\u0000\u0000\u0000\u00a1\u00a2"+
		"\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000\u00a2\u00a3"+
		"\u0001\u0000\u0000\u0000\u00a3\u00a5\u0001\u0000\u0000\u0000\u00a4\u00a6"+
		"\u0003\u001c\u000e\u0000\u00a5\u00a4\u0001\u0000\u0000\u0000\u00a6\u00a7"+
		"\u0001\u0000\u0000\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000\u00a7\u00a8"+
		"\u0001\u0000\u0000\u0000\u00a8\u00a9\u0001\u0000\u0000\u0000\u00a9\u00aa"+
		"\u0005 \u0000\u0000\u00aa\u001b\u0001\u0000\u0000\u0000\u00ab\u00ac\u0005"+
		"\u000e\u0000\u0000\u00ac\u00ad\u0005&\u0000\u0000\u00ad\u00ae\u0005\u001e"+
		"\u0000\u0000\u00ae\u00b3\u0005&\u0000\u0000\u00af\u00b0\u0005\u0010\u0000"+
		"\u0000\u00b0\u00b2\u0005&\u0000\u0000\u00b1\u00af\u0001\u0000\u0000\u0000"+
		"\u00b2\u00b5\u0001\u0000\u0000\u0000\u00b3\u00b1\u0001\u0000\u0000\u0000"+
		"\u00b3\u00b4\u0001\u0000\u0000\u0000\u00b4\u00b6\u0001\u0000\u0000\u0000"+
		"\u00b5\u00b3\u0001\u0000\u0000\u0000\u00b6\u00b7\u0005!\u0000\u0000\u00b7"+
		"\u00b8\u0005%\u0000\u0000\u00b8\u00b9\u0005\"\u0000\u0000\u00b9\u001d"+
		"\u0001\u0000\u0000\u0000\u00ba\u00bb\u0005\u0011\u0000\u0000\u00bb\u00bc"+
		"\u0005\u001e\u0000\u0000\u00bc\u00bd\u0007\u0001\u0000\u0000\u00bd\u001f"+
		"\u0001\u0000\u0000\u0000\u00be\u00bf\u0005\u0014\u0000\u0000\u00bf\u00c0"+
		"\u0005\u001f\u0000\u0000\u00c0\u00c1\u0003\u0006\u0003\u0000\u00c1\u00c3"+
		"\u0003$\u0012\u0000\u00c2\u00c4\u0003\"\u0011\u0000\u00c3\u00c2\u0001"+
		"\u0000\u0000\u0000\u00c4\u00c5\u0001\u0000\u0000\u0000\u00c5\u00c3\u0001"+
		"\u0000\u0000\u0000\u00c5\u00c6\u0001\u0000\u0000\u0000\u00c6\u00c7\u0001"+
		"\u0000\u0000\u0000\u00c7\u00c8\u0005 \u0000\u0000\u00c8!\u0001\u0000\u0000"+
		"\u0000\u00c9\u00ca\u0005\u000e\u0000\u0000\u00ca\u00cb\u0005&\u0000\u0000"+
		"\u00cb\u00cc\u0005\u001e\u0000\u0000\u00cc\u00cd\u0005(\u0000\u0000\u00cd"+
		"\u00ce\u0005!\u0000\u0000\u00ce\u00cf\u0005%\u0000\u0000\u00cf\u00d0\u0005"+
		"\"\u0000\u0000\u00d0#\u0001\u0000\u0000\u0000\u00d1\u00d2\u0005\u0015"+
		"\u0000\u0000\u00d2\u00d3\u0005\'\u0000\u0000\u00d3%\u0001\u0000\u0000"+
		"\u0000\u00d4\u00d5\u0005\u0016\u0000\u0000\u00d5\u00d6\u0005\u001f\u0000"+
		"\u0000\u00d6\u00d8\u0003\u0006\u0003\u0000\u00d7\u00d9\u0003(\u0014\u0000"+
		"\u00d8\u00d7\u0001\u0000\u0000\u0000\u00d8\u00d9\u0001\u0000\u0000\u0000"+
		"\u00d9\u00db\u0001\u0000\u0000\u0000\u00da\u00dc\u0003\u001c\u000e\u0000"+
		"\u00db\u00da\u0001\u0000\u0000\u0000\u00dc\u00dd\u0001\u0000\u0000\u0000"+
		"\u00dd\u00db\u0001\u0000\u0000\u0000\u00dd\u00de\u0001\u0000\u0000\u0000"+
		"\u00de\u00df\u0001\u0000\u0000\u0000\u00df\u00e0\u0005 \u0000\u0000\u00e0"+
		"\'\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005\u0017\u0000\u0000\u00e2\u00e3"+
		"\u0005\u001e\u0000\u0000\u00e3\u00e4\u0005%\u0000\u0000\u00e4)\u0001\u0000"+
		"\u0000\u0000\u00e5\u00e6\u0005\u0018\u0000\u0000\u00e6\u00e7\u0005\u001f"+
		"\u0000\u0000\u00e7\u00e9\u0003\u0006\u0003\u0000\u00e8\u00ea\u0003,\u0016"+
		"\u0000\u00e9\u00e8\u0001\u0000\u0000\u0000\u00ea\u00eb\u0001\u0000\u0000"+
		"\u0000\u00eb\u00e9\u0001\u0000\u0000\u0000\u00eb\u00ec\u0001\u0000\u0000"+
		"\u0000\u00ec\u00ee\u0001\u0000\u0000\u0000\u00ed\u00ef\u00030\u0018\u0000"+
		"\u00ee\u00ed\u0001\u0000\u0000\u0000\u00ef\u00f0\u0001\u0000\u0000\u0000"+
		"\u00f0\u00ee\u0001\u0000\u0000\u0000\u00f0\u00f1\u0001\u0000\u0000\u0000"+
		"\u00f1\u00f2\u0001\u0000\u0000\u0000\u00f2\u00f3\u0005 \u0000\u0000\u00f3"+
		"+\u0001\u0000\u0000\u0000\u00f4\u00f5\u0005\u0019\u0000\u0000\u00f5\u00f6"+
		"\u0007\u0002\u0000\u0000\u00f6\u00f8\u0005\u001f\u0000\u0000\u00f7\u00f9"+
		"\u0003.\u0017\u0000\u00f8\u00f7\u0001\u0000\u0000\u0000\u00f9\u00fa\u0001"+
		"\u0000\u0000\u0000\u00fa\u00f8\u0001\u0000\u0000\u0000\u00fa\u00fb\u0001"+
		"\u0000\u0000\u0000\u00fb\u00fc\u0001\u0000\u0000\u0000\u00fc\u00fd\u0005"+
		" \u0000\u0000\u00fd-\u0001\u0000\u0000\u0000\u00fe\u00ff\u0005\u001a\u0000"+
		"\u0000\u00ff\u0100\u0005&\u0000\u0000\u0100\u0101\u0005\u001e\u0000\u0000"+
		"\u0101\u0102\u0005(\u0000\u0000\u0102/\u0001\u0000\u0000\u0000\u0103\u0104"+
		"\u0005\u001b\u0000\u0000\u0104\u0105\u0005&\u0000\u0000\u0105\u0107\u0005"+
		"\u001f\u0000\u0000\u0106\u0108\u0003\u001c\u000e\u0000\u0107\u0106\u0001"+
		"\u0000\u0000\u0000\u0108\u0109\u0001\u0000\u0000\u0000\u0109\u0107\u0001"+
		"\u0000\u0000\u0000\u0109\u010a\u0001\u0000\u0000\u0000\u010a\u010b\u0001"+
		"\u0000\u0000\u0000\u010b\u010c\u00032\u0019\u0000\u010c\u010d\u0005 \u0000"+
		"\u0000\u010d1\u0001\u0000\u0000\u0000\u010e\u010f\u0005\u001c\u0000\u0000"+
		"\u010f\u0110\u0005\u001e\u0000\u0000\u0110\u0111\u0007\u0002\u0000\u0000"+
		"\u01113\u0001\u0000\u0000\u0000\u0112\u0113\u0005\u001d\u0000\u0000\u0113"+
		"\u0114\u0005\u001f\u0000\u0000\u0114\u0115\u0003\u0006\u0003\u0000\u0115"+
		"\u0116\u00036\u001b\u0000\u0116\u0117\u0005 \u0000\u0000\u01175\u0001"+
		"\u0000\u0000\u0000\u0118\u0119\u0005\u000e\u0000\u0000\u0119\u011a\u0005"+
		"&\u0000\u0000\u011a\u011b\u0005\u001e\u0000\u0000\u011b\u011c\u0005\'"+
		"\u0000\u0000\u011c\u011d\u0005!\u0000\u0000\u011d\u011e\u0005%\u0000\u0000"+
		"\u011e\u011f\u0005\"\u0000\u0000\u011f7\u0001\u0000\u0000\u0000\u0012"+
		"?J`eow|\u0081\u00a2\u00a7\u00b3\u00c5\u00d8\u00dd\u00eb\u00f0\u00fa\u0109";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}