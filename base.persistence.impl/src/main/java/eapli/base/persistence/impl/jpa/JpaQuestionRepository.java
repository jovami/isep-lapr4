package eapli.base.persistence.impl.jpa;

import eapli.base.exam.domain.question.Question;
import eapli.base.exam.domain.question.QuestionRepository;

/**
 * JpaQuestionRepository
 */
class JpaQuestionRepository extends BaseJpaRepositoryBase<Question, Long, Long> implements QuestionRepository {

    JpaQuestionRepository(String identityFieldName) {
        super(identityFieldName, "questionId");
    }
}
