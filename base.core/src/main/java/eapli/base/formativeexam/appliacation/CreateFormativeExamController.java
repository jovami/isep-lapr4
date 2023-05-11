package eapli.base.formativeexam.appliacation;

import eapli.base.formativeexam.domain.FormativeExamFactory;
import eapli.base.formativeexam.domain.grammar.FormativeExamSpecificationFactory;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;

/**
 * CreateFormativeExamController
 */
public class CreateFormativeExamController {
    private final RepositoryFactory repoFactory;

    public CreateFormativeExamController() {
        this.repoFactory = PersistenceContext.repositories();
    }

    public void createFormativeExam(String filePath) {
        var spec = new FormativeExamSpecificationFactory().build(filePath);
        this.repoFactory.formativeExamSpecifications().save(spec);
        var questions = this.repoFactory.questions().findAll();
        var exam = new FormativeExamFactory().build(spec, questions);
        this.repoFactory.formativeExams().save(exam);
        // TODO: return something to the UI
    }
}
