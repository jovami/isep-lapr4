package eapli.base.enrollmentrequest.application;

import eapli.base.course.application.ListCoursesService;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseName;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 * EnrollmentRequestController
 */
public final class EnrollmentRequestController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final EnrollmentRequestRepository enrollmentRequestRepo;
    private final CourseRepository courseRepo;
    private EnrollmentRequest enrollmentRequest;

    public EnrollmentRequestController() {
        this.enrollmentRequestRepo = PersistenceContext.repositories().enrollmentRequests();
        this.courseRepo = PersistenceContext.repositories().courses();
        enrollmentRequest = null;
    }

    public Iterable<Course> getCourses() {
        return new ListCoursesService(courseRepo).enrollable();
    }

    public boolean createEnrollmentRequest(CourseName courseName){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);
        var session = authz.session();
        if (session.isEmpty()) {
            return false;
        }

        var username = session.get().authenticatedUser().username();
        try {
            enrollmentRequest = new EnrollmentRequest(courseName, username);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // can be used, for example, by a power user to create an enrollment request for a student
    public boolean createEnrollmentRequest(CourseName courseName, Username username){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER);
        try {
            enrollmentRequest = new EnrollmentRequest(courseName, username);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean saveEnrollmentRequest(){
        if (enrollmentRequest == null) {
            return false;
        }
        enrollmentRequest = this.enrollmentRequestRepo.save(enrollmentRequest);
        return true;
    }
}