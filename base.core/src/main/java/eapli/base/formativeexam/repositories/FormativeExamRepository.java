package eapli.base.formativeexam.repositories;

import java.util.Set;

import eapli.base.course.domain.Course;
import eapli.base.formativeexam.domain.FormativeExam;
import eapli.framework.domain.repositories.DomainRepository;

/**
 * FormativeExamRepository
 */
public interface FormativeExamRepository extends DomainRepository<Long, FormativeExam> {

    Iterable<FormativeExam> formativeExamsOfCourses(Set<Course> courses);
}
