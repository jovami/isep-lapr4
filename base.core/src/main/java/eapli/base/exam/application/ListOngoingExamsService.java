package eapli.base.exam.application;

import static org.eclipse.collections.impl.block.factory.HashingStrategies.fromFunction;

import java.util.HashSet;

import org.eclipse.collections.impl.factory.HashingStrategySets;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.exam.domain.RegularExam;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.examresult.repository.RegularExamResultRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

public class ListOngoingExamsService {
    private final EnrollmentRepository enrollmentRepo;
    private final RegularExamRepository examRepo;
    private final RegularExamResultRepository resultRepo;

    public ListOngoingExamsService() {
        final var repos = PersistenceContext.repositories();
        this.enrollmentRepo = repos.enrollments();
        this.examRepo = repos.regularExams();
        this.resultRepo = repos.examResults();
    }

    public Iterable<RegularExam> forStudent(Student s) {
        var courses = enrollmentRepo.ongoingCoursesOfStudent(s);
        var courseSet = new HashSet<Course>();
        courses.forEach(courseSet::add);

        var exams = HashingStrategySets.mutable.withAll(
                fromFunction(RegularExam::identity),
                this.examRepo.ongoingExams(courseSet));

        exams.removeAllIterable(this.resultRepo.completedExams(s));

        return exams;
    }
}
