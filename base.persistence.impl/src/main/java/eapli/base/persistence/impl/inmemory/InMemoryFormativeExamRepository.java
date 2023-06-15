package eapli.base.persistence.impl.inmemory;

import java.util.Set;

import eapli.base.course.domain.Course;
import eapli.base.formativeexam.domain.FormativeExam;
import eapli.base.formativeexam.domain.FormativeExamTitle;
import eapli.base.formativeexam.repositories.FormativeExamRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 * InMemoryFormativeExamRepository
 */
class InMemoryFormativeExamRepository extends InMemoryDomainRepository<FormativeExam, FormativeExamTitle>
        implements FormativeExamRepository {
    static {
        InMemoryInitializer.init();
    }

    public InMemoryFormativeExamRepository() {
        super();
    }

    @Override
    public Iterable<FormativeExam> formativeExamsOfCourses(Set<Course> courses) {
        return match(exam -> courses.contains(exam.course()));
    }
}
