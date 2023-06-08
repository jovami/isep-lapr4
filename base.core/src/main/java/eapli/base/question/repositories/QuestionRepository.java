package eapli.base.question.repositories;

import eapli.base.course.domain.Course;
import eapli.base.question.domain.Question;
import eapli.framework.domain.repositories.DomainRepository;

/**
 * FormativeExamRepository
 */
public interface QuestionRepository extends DomainRepository<Long, Question> {

    Iterable<Question> questionsOfCourse(Course course);
}
