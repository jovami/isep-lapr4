package eapli.base.exam.repositories;


import eapli.base.course.domain.Course;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamDate;
import eapli.framework.domain.repositories.DomainRepository;

public interface RegularExamRepository extends DomainRepository<Integer, RegularExam> {
    Iterable<RegularExam> findByCourse(Course course);
}
