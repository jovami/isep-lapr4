package eapli.base.persistence.impl.inmemory;

import eapli.base.course.domain.Course;
import eapli.base.question.domain.Question;
import eapli.base.question.repositories.QuestionRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 * InMemoryQuestionRepository
 */
class InMemoryQuestionRepository extends InMemoryDomainRepository<Question, Long> implements QuestionRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryQuestionRepository() {
        super();
    }

    @Override
    public Iterable<Question> questionsOfCourse(Course course) {
        return match(q -> q.course().sameAs(course));
    }
}
