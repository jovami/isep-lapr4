package eapli.base.exam.repositories;

import java.time.LocalDateTime;
import java.util.Set;

import eapli.base.course.domain.Course;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.framework.domain.repositories.DomainRepository;

public interface RegularExamRepository extends DomainRepository<Integer, RegularExam> {

    Iterable<RegularExam> findByCourse(Course course);

    Iterable<RegularExam> examsOfCoursesAfterTime(LocalDateTime time, Set<Course> courses);

    Iterable<RegularExam> ongoingExams(Set<Course> courses);
}
