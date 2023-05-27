package eapli.base.persistence.impl.inmemory;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.examresult.domain.RegularExamResult;
import eapli.base.examresult.repository.RegularExamResultRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryRegularExamResultRepository extends InMemoryDomainRepository<RegularExamResult, Long> implements RegularExamResultRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryRegularExamResultRepository() {
        super();
    }

    @Override
    public Iterable<RegularExamResult> examResultsByStudent(Student s) {
        return match(e -> e.student().equals(s));
    }

    @Override
    public Iterable<RegularExamResult> regularExamResultsByCourse(Course c) {
        return match(e -> e.regularExam().course().equals(c));
    }
}
