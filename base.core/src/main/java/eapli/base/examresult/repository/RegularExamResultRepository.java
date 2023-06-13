package eapli.base.examresult.repository;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.exam.domain.RegularExam;
import eapli.base.examresult.domain.RegularExamResult;
import eapli.framework.domain.repositories.DomainRepository;

/**
 * FormativeExamRepository
 */
public interface RegularExamResultRepository extends DomainRepository<Long, RegularExamResult> {

    Iterable<RegularExamResult> examResultsByStudent(Student s);

    Iterable<RegularExamResult> regularExamResultsByCourse(Course c);

    Iterable<RegularExam> completedExams(Student s);
}
