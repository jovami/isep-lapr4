package eapli.base.persistence.impl.jpa;

import eapli.base.course.domain.Course;
import eapli.base.exam.domain.RegularExam;
import eapli.base.exam.domain.RegularExamTitle;
import eapli.base.exam.repositories.RegularExamRepository;

import java.time.LocalDateTime;
import java.util.Set;

class JpaRegularExamRepository extends BaseJpaRepositoryBase<RegularExam, Long, RegularExamTitle>
        implements RegularExamRepository {

    JpaRegularExamRepository(String identityFieldName) {
        super(identityFieldName, "title");
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
        return match("e.course IN :courses AND e.date.startDate > :time",
                "courses", courses, "time", time);
    }

    @Override
    public Iterable<RegularExam> ongoingExams(Set<Course> courses) {
        return match("e.course IN :courses AND e.date.startDate < :time AND e.date.endDate > :time",
                "courses", courses, "time", LocalDateTime.now());
    }
}
