package eapli.base.persistence.impl.inmemory;

import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import eapli.base.exam.repositories.RegularExamRepository;

public class InMemoryRegularExamRepository extends InMemoryDomainRepository<RegularExam,Integer> implements RegularExamRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryRegularExamRepository(){

    }
}
