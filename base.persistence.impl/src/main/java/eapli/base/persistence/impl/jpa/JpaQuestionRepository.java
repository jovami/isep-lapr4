package eapli.base.persistence.impl.jpa;

import eapli.base.question.domain.Question;
import eapli.base.question.repositories.QuestionRepository;

/**
 * JpaQuestionRepository
 */
class JpaQuestionRepository extends BaseJpaRepositoryBase<Question, Long, Long> implements QuestionRepository {

    JpaQuestionRepository(String identityFieldName) {
        super(identityFieldName, "questionId");
    }
}
