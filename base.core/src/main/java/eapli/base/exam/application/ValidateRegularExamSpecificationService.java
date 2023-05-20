package eapli.base.exam.application;

import eapli.base.exam.application.parser.autogen.ExamSpecLexer;
import eapli.base.exam.application.parser.autogen.ExamSpecParser;
import jovami.util.grammar.AbstractGrammarValidator;

public class ValidateRegularExamSpecificationService extends AbstractGrammarValidator {

    public ValidateRegularExamSpecificationService() {
        super(ExamSpecLexer::new, (stream, err) -> {
            var parser = new ExamSpecParser(stream);
            parser.addErrorListener(err);
            return parser::exam;
        });
    }
}
