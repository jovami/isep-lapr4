package eapli.base.course.application;

import static org.eclipse.collections.impl.block.factory.HashingStrategies.fromFunction;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import org.eclipse.collections.impl.factory.HashingStrategySets;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseState;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

/**
 * ListCoursesService
 */
public final class ListCoursesService {
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollRepo;
    private final EnrollmentRequestRepository requestRepo;

    @Deprecated
    public ListCoursesService(final CourseRepository repo) {
        this.courseRepo = repo;
        this.enrollRepo = null;
        this.requestRepo = null;
    }

    public ListCoursesService() {
        final var repos = PersistenceContext.repositories();
        this.courseRepo = repos.courses();
        this.enrollRepo = repos.enrollments();
        this.requestRepo = repos.enrollmentRequests();
    }

    @Deprecated
    public Iterable<Course> openable() {
        return this.courseRepo.ofState(CourseState.CLOSE);
    }

    @Deprecated
    public Iterable<Course> openableToEnrollments() {
        return this.courseRepo.ofState(CourseState.OPEN);
    }

    @Deprecated
    public Iterable<Course> enrollable() {
        return this.courseRepo.ofState(CourseState.ENROLL);
    }

    @Deprecated
    public Iterable<Course> closable() {
        return withStates(CourseState.CLOSE, CourseState.OPEN, CourseState.ENROLL, CourseState.INPROGRESS);
    }

    @Deprecated
    public Iterable<Course> withStates(Set<CourseState> states) {
        return this.courseRepo.ofStates(states);
    }

    @Deprecated
    public Iterable<Course> withStates(CourseState... states) {
        return withStates(Arrays.stream(states).collect(Collectors.toUnmodifiableSet()));
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
