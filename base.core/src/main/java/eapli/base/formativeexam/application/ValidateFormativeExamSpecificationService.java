package eapli.base.formativeexam.application;

import eapli.base.formativeexam.application.parser.autogen.FormativeExamLexer;
import eapli.base.formativeexam.application.parser.autogen.FormativeExamParser;
import jovami.util.grammar.AbstractGrammarValidator;

/**
 * ValidateFormativeExamSpecification
 */
public final class ValidateFormativeExamSpecificationService extends AbstractGrammarValidator {

    public ValidateFormativeExamSpecificationService() {
        super(FormativeExamLexer::new, (stream, err) -> {
            var parser = new FormativeExamParser(stream);
            parser.addErrorListener(err);
            return parser::exam;
        });
    }
}
