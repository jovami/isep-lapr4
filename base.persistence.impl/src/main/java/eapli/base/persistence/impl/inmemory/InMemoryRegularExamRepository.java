package eapli.base.persistence.impl.inmemory;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import eapli.base.course.domain.Course;
import eapli.base.exam.domain.RegularExam;
import eapli.base.exam.domain.RegularExamTitle;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

class InMemoryRegularExamRepository extends InMemoryDomainRepository<RegularExam, RegularExamTitle>
        implements RegularExamRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryRegularExamRepository() {

    }

    @Override
    public Iterable<RegularExam> findByCourse(Course course) {
        return match((regularExam -> course.equals(regularExam.course())));
    }

    @Override
    public Iterable<RegularExam> examsOfCoursesAfterTime(LocalDateTime time, Set<Course> courses) {
        return valuesStream()
                .filter(exam -> courses.contains(exam.course()) && exam.date().openDate().isAfter(time))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<RegularExam> ongoingExams(Set<Course> courses) {
        return valuesStream()
                .filter(exam -> courses.contains(exam.course())
                        && exam.date().openDate().isBefore(LocalDateTime.now())
                        && exam.date().closeDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
}
