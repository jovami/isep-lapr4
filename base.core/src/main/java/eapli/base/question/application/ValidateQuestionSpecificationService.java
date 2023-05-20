package eapli.base.question.application;

import eapli.base.question.application.parser.autogen.QuestionLexer;
import eapli.base.question.application.parser.autogen.QuestionParser;
import jovami.util.grammar.AbstractGrammarValidator;

/**
 * ValidateQuestionSpecification
 */
public final class ValidateQuestionSpecificationService extends AbstractGrammarValidator {

    public ValidateQuestionSpecificationService() {
        super(QuestionLexer::new, (stream, err) -> {
            var parser = new QuestionParser(stream);
            parser.addErrorListener(err);
            return parser::question;
        });
    }
}
