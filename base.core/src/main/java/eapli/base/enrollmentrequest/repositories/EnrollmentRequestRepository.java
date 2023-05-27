package eapli.base.enrollmentrequest.repositories;

import java.util.Optional;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.framework.domain.repositories.DomainRepository;

public interface EnrollmentRequestRepository extends DomainRepository<Integer, EnrollmentRequest> {

    Iterable<EnrollmentRequest> pendingEnrollmentRequests();

    Iterable<Course> coursesOfEnrollmentRequestsByStudent(Student s);

    Optional<EnrollmentRequest> findFirstPendingEnrollmentRequest();
}
