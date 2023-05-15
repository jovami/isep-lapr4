package eapli.base.enrollmentrequest.application;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.course.application.ListCoursesService;
import eapli.base.course.domain.Course;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 * EnrollmentRequestController
 */
public final class EnrollmentRequestController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final EnrollmentRequestRepository enrollmentRequestRepo;
    private final CourseRepository courseRepo;
    private final StudentRepository studentRepo;
    private EnrollmentRequest enrollmentRequest;


    public EnrollmentRequestController() {
        this.enrollmentRequestRepo = PersistenceContext.repositories().enrollmentRequests();
        this.courseRepo = PersistenceContext.repositories().courses();
        this.studentRepo = PersistenceContext.repositories().students();
        enrollmentRequest = null;
    }

    public Iterable<Course> getCourses() {
        return new ListCoursesService(courseRepo).enrollable();
    }

    public boolean createEnrollmentRequest(Course course){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);
        var session = authz.session();
        if (session.isEmpty()) {
            return false;
        }

        var student = studentRepo.findBySystemUser(session.get().authenticatedUser());
        if (student.isEmpty()){
            return false;
        }
        enrollmentRequest = new EnrollmentRequest(course, student.get());
        return true;
    }

    // can be used, for example, by a power user to create an enrollment request for a student
    public boolean createEnrollmentRequest(Course course, Student student){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER);
        enrollmentRequest = new EnrollmentRequest(course, student);
        return true;
    }

    public boolean saveEnrollmentRequest(){
        if (enrollmentRequest == null) {
            return false;
        }
        enrollmentRequest = this.enrollmentRequestRepo.save(enrollmentRequest);
        return true;
    }
}