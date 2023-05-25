package eapli.base.enrollmentrequest.application;

import java.util.Optional;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.application.ListCoursesService;
import eapli.base.course.domain.Course;
import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;

/**
 * EnrollmentRequestController
 */
@UseCaseController
public final class EnrollmentRequestController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final EnrollmentRequestRepository enrollmentRequestRepo;
    private final StudentRepository studentRepo;
    private final ListCoursesService svc;

    public EnrollmentRequestController() {
        this.enrollmentRequestRepo = PersistenceContext.repositories().enrollmentRequests();
        this.studentRepo = PersistenceContext.repositories().students();
        this.svc = new ListCoursesService();
    }

    public Iterable<Course> getEnrollableCourses() {
        return this.svc.studentCanRequestEnroll(getStudent(this.authz.session().orElseThrow()).orElseThrow());
    }

    public Iterable<EnrollmentRequest> findAllRequests() {
        return this.enrollmentRequestRepo.findAll();
    }

    public boolean createEnrollmentRequest(Course course) {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);
        var session = this.authz.session();
        if (session.isEmpty())
            return false;

        var student = getStudent(session.get());
        if (student.isEmpty()) {
            System.out.println("Student not found");
            return false;
        }
        var enrollmentRequest = new EnrollmentRequest(course, student.get());
        this.enrollmentRequestRepo.save(enrollmentRequest);
        return true;
    }

    private Optional<Student> getStudent(UserSession session) {
        return this.studentRepo.findBySystemUser(session.authenticatedUser());
    }
}
