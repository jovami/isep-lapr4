package eapli.base.formativeexam.repositories;

import java.util.Set;

import eapli.base.course.domain.Course;
import eapli.base.formativeexam.domain.FormativeExam;
import eapli.base.formativeexam.domain.FormativeExamTitle;
import eapli.framework.domain.repositories.DomainRepository;

/**
 * FormativeExamRepository
 */
public interface FormativeExamRepository extends DomainRepository<FormativeExamTitle, FormativeExam> {

    Iterable<FormativeExam> formativeExamsOfCourses(Set<Course> courses);
}
