package eapli.base.persistence.impl.jpa;

import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.repositories.RegularExamRepository;

public class JpaRegularExamRepository extends BaseJpaRepositoryBase<RegularExam,Long,Integer> implements RegularExamRepository {
    JpaRegularExamRepository(String persistenceUnitName){
        super(persistenceUnitName,"examid");
    }

    //TODO: implement
}
