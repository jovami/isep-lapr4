package eapli.base.persistence.impl.inmemory;

import eapli.base.formativeexam.domain.FormativeExam;
import eapli.base.formativeexam.repositories.FormativeExamRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 * InMemoryFormativeExamRepository
 */
public class InMemoryFormativeExamRepository extends InMemoryDomainRepository<FormativeExam, Long> implements FormativeExamRepository {
    static {
        InMemoryInitializer.init();
    }

    public InMemoryFormativeExamRepository() {
        super();
    }

}
