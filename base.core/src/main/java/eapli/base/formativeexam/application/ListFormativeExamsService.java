package eapli.base.formativeexam.application;

import static org.eclipse.collections.impl.block.factory.HashingStrategies.fromFunction;

import org.eclipse.collections.impl.factory.HashingStrategySets;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.formativeexam.domain.FormativeExam;
import eapli.base.formativeexam.repositories.FormativeExamRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

/**
 * ListFormativeExamsService
 */
public final class ListFormativeExamsService {
    private final FormativeExamRepository fexamRepo;
    private final EnrollmentRepository enrollRepo;

    public ListFormativeExamsService() {
        super();

        var repos = PersistenceContext.repositories();
        this.enrollRepo = repos.enrollments();
        this.fexamRepo = repos.formativeExams();
    }

    public Iterable<FormativeExam> forStudent(Student s) {
        var courses = HashingStrategySets.mutable.withAll(
            fromFunction(Course::identity),
            this.enrollRepo.ongoingCoursesOfStudent(s));

        return this.fexamRepo.formativeExamsOfCourses(courses);
    }
}
