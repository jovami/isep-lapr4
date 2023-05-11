package eapli.base.persistence.impl.inmemory;

import eapli.base.exam.domain.question.Question;
import eapli.base.exam.domain.question.QuestionRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 * InMemoryQuestionRepository
 */
public class InMemoryQuestionRepository extends InMemoryDomainRepository<Question, Long> implements QuestionRepository{

    static {
        InMemoryInitializer.init();
    }

    public InMemoryQuestionRepository() {
        super();
    }
}
