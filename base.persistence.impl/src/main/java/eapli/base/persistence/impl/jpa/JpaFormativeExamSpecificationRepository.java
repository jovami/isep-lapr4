package eapli.base.persistence.impl.jpa;

import eapli.base.formativeexam.domain.FormativeExamSpecification;
import eapli.base.formativeexam.repositories.FormativeExamSpecificationRepository;

/**
 * JpaFormativeExamSpecificationRepository
 */
public class JpaFormativeExamSpecificationRepository extends BaseJpaRepositoryBase<FormativeExamSpecification, Long, Long> implements FormativeExamSpecificationRepository {

    JpaFormativeExamSpecificationRepository(String identityFieldName) {
        super(identityFieldName, "formativeExamSpecificationId");
    }


}
