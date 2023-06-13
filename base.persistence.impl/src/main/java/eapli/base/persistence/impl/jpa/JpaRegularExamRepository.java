package eapli.base.persistence.impl.jpa;

import java.time.LocalDateTime;
import java.util.Set;

import eapli.base.course.domain.Course;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.repositories.RegularExamRepository;

class JpaRegularExamRepository extends BaseJpaRepositoryBase<RegularExam, Long, Integer>
        implements RegularExamRepository {

    JpaRegularExamRepository(String identityFieldName) {
        super(identityFieldName, "id");
    }

    @Override
    public Iterable<RegularExam> findByCourse(Course course) {
        final var query = entityManager().createQuery(
                "SELECT e FROM RegularExam e WHERE e.course = :course",
                RegularExam.class);
        query.setParameter("course", course);
        return query.getResultList();
    }

    @Override
    public Iterable<RegularExam> examsOfCoursesAfterTime(LocalDateTime time, Set<Course> courses) {
        return match("e.course IN :courses AND e.date.start > :time",
                "courses", courses, "time", time);
    }

    @Override
    public Iterable<RegularExam> ongoingExams(Set<Course> courses) {
        return match("e.course IN :courses AND e.date.start < :time AND e.date.end > :time",
                "courses", courses, "time", LocalDateTime.now());
    }
}
