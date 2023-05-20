package eapli.base.enrollmentrequest.application;

import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.application.ListCoursesService;
import eapli.base.course.domain.Course;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

/**
 * EnrollmentRequestController
 */
@UseCaseController
public final class EnrollmentRequestController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final EnrollmentRequestRepository enrollmentRequestRepo;
    private final CourseRepository courseRepo;
    private final StudentRepository studentRepo;
    private final UserRepository userRepository;

    public EnrollmentRequestController() {
        this.enrollmentRequestRepo = PersistenceContext.repositories().enrollmentRequests();
        this.courseRepo = PersistenceContext.repositories().courses();
        this.studentRepo = PersistenceContext.repositories().students();
        this.userRepository = PersistenceContext.repositories().users();
    }

    public Iterable<Course> getEnrollableCourses() {
        return new ListCoursesService(courseRepo).openableToEnrollments();
    }

    public Iterable<EnrollmentRequest> findAllRequests() {
        return this.enrollmentRequestRepo.findAll();
    }

    public boolean createEnrollmentRequest(Course course) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);
        var session = authz.session();
        if (session.isEmpty()) {
            return false;
        }

        var student = studentRepo.findBySystemUser(session.get().authenticatedUser());
        if (student.isEmpty()) {
            System.out.println("Student not found");
            return false;
        }
        var enrollmentRequest = new EnrollmentRequest(course, student.get());
        this.enrollmentRequestRepo.save(enrollmentRequest);
        return true;
    }
}
