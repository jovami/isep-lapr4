package eapli.base.persistence.impl.inmemory;

import eapli.base.formativeexam.domain.FormativeExamSpecification;
import eapli.base.formativeexam.repositories.FormativeExamSpecificationRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 * InMemoryFormativeExamSpecificationRepository
 */
public class InMemoryFormativeExamSpecificationRepository extends InMemoryDomainRepository<FormativeExamSpecification, Long> implements FormativeExamSpecificationRepository{

    static {
        InMemoryInitializer.init();
    }

    public InMemoryFormativeExamSpecificationRepository() {
        super();
    }
}
