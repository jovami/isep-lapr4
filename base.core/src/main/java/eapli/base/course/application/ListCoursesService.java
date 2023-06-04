package eapli.base.course.application;

import static org.eclipse.collections.impl.block.factory.HashingStrategies.fromFunction;

import org.eclipse.collections.impl.factory.HashingStrategySets;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

/**
 * ListCoursesService
 */
public final class ListCoursesService {
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollRepo;
    private final EnrollmentRequestRepository requestRepo;

    public ListCoursesService() {
        final var repos = PersistenceContext.repositories();
        this.courseRepo = repos.courses();
        this.enrollRepo = repos.enrollments();
        this.requestRepo = repos.enrollmentRequests();
    }

    public Iterable<Course> studentCanRequestEnroll(final Student s) {
        final var enrollable = HashingStrategySets.mutable.withAll(
                fromFunction(Course::identity),
                this.courseRepo.enrollable());

        final var enrolled = HashingStrategySets.mutable.withAll(
                fromFunction(Course::identity),
                this.requestRepo.coursesOfEnrollmentRequestsByStudent(s));

        return enrollable.difference(enrolled);
    }

    public Iterable<Course> studentIsEnrolledOrCanEnroll(final Student s) {
        // Courses that the student can enroll in
        final var courses = HashingStrategySets.mutable.withAll(
                fromFunction(Course::identity),
                this.courseRepo.enrollable());

        // those that the student is already enrolled
        courses.addAllIterable(this.enrollRepo.coursesOfEnrolledStudent(s));
        return courses;
    }
}
