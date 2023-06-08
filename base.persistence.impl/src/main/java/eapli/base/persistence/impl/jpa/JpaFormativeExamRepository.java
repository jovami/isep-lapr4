package eapli.base.persistence.impl.jpa;

import java.util.Set;

import eapli.base.course.domain.Course;
import eapli.base.formativeexam.domain.FormativeExam;
import eapli.base.formativeexam.repositories.FormativeExamRepository;

/**
 * JpaFormativeExamRepository
 */
class JpaFormativeExamRepository extends BaseJpaRepositoryBase<FormativeExam, Long, Long>
        implements FormativeExamRepository {

    JpaFormativeExamRepository(String identityFieldName) {
        super(identityFieldName, "id");
    }

    @Override
    public Iterable<FormativeExam> formativeExamsOfCourses(Set<Course> courses) {
        return match("e.course IN :courses", "courses", courses);
    }
}
