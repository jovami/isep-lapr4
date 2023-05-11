package eapli.base.persistence.impl.jpa;

import java.util.Optional;

import eapli.base.formativeexam.domain.FormativeExam;
import eapli.base.formativeexam.repositories.FormativeExamRepository;

/**
 * JpaFormativeExamRepository
 */
public class JpaFormativeExamRepository extends BaseJpaRepositoryBase<FormativeExam, Long, Long> implements FormativeExamRepository {

    JpaFormativeExamRepository(String identityFieldName) {
        super(identityFieldName, "formativeExamId");
    }
}
