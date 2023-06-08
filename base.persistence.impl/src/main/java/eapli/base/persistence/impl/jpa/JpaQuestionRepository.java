package eapli.base.persistence.impl.jpa;

import eapli.base.course.domain.Course;
import eapli.base.question.domain.Question;
import eapli.base.question.repositories.QuestionRepository;

/**
 * JpaQuestionRepository
 */
class JpaQuestionRepository extends BaseJpaRepositoryBase<Question, Long, Long> implements QuestionRepository {

    JpaQuestionRepository(String identityFieldName) {
        super(identityFieldName, "questionId");
    }


    @Override
    public Iterable<Question> questionsOfCourse(Course course) {
        return match("e.course = :course", "course", course);
    }
}
