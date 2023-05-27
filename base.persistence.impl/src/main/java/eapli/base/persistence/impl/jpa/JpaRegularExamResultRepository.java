package eapli.base.persistence.impl.jpa;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.examresult.domain.RegularExamResult;
import eapli.base.examresult.repository.RegularExamResultRepository;

public class JpaRegularExamResultRepository extends BaseJpaRepositoryBase<RegularExamResult, Long, Long>
        implements RegularExamResultRepository {

    JpaRegularExamResultRepository(String identityFieldName) {
        super(identityFieldName, "id");
    }

    @Override
    public Iterable<RegularExamResult> examResultsByStudent(Student s) {
        return match("e.student = :student", "student", s);
    }

    @Override
    public Iterable<RegularExamResult> regularExamResultsByCourse(Course c) {
        return match("e.regularExam.course = :course", "course", c);
    }
}
