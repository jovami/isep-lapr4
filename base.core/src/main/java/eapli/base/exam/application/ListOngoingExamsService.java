package eapli.base.exam.application;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

import java.util.HashSet;

public class ListOngoingExamsService {
    EnrollmentRepository enrollmentRepo;
    RegularExamRepository examRepo;

    public ListOngoingExamsService() {
        final var repos = PersistenceContext.repositories();
        this.enrollmentRepo = repos.enrollments();
        this.examRepo = repos.regularExams();
    }

    // TODO: we still have to subtract the exams that the student has already done
    public Iterable<RegularExam> forStudent(Student s) {
        var courses = enrollmentRepo.ongoingCoursesOfStudent(s);
        var courseSet = new HashSet<Course>();
        courses.forEach(courseSet::add);

        return this.examRepo.ongoingExams(courseSet);
    }
}