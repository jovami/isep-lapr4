package eapli.base.enrollmentrequest.application;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.application.ListCoursesService;
import eapli.base.course.domain.Course;
import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 * EnrollmentRequestController
 */
@UseCaseController
public final class EnrollmentRequestController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final EnrollmentRequestRepository enrollmentRequestRepo;
    private final ListCoursesService svc;
    private final MyUserService userSvc;

    public EnrollmentRequestController() {
        this.enrollmentRequestRepo = PersistenceContext.repositories().enrollmentRequests();
        this.svc = new ListCoursesService();
        this.userSvc = new MyUserService();
    }

    public Iterable<Course> getEnrollableCourses() {
        return this.svc.studentCanRequestEnroll(this.userSvc.currentStudent());
    }

    public Iterable<EnrollmentRequest> findAllRequests() {
        return this.enrollmentRequestRepo.findAll();
    }

    public boolean createEnrollmentRequest(Course course) {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);

        var enrollmentRequest = new EnrollmentRequest(course, this.userSvc.currentStudent());
        this.enrollmentRequestRepo.save(enrollmentRequest);
        return true;
    }
}
