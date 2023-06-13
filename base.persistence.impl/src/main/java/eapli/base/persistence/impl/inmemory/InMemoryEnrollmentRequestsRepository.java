package eapli.base.persistence.impl.inmemory;

import java.util.Optional;
import java.util.stream.Collectors;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.enrollmentrequest.domain.EnrollmentRequest;
import eapli.base.enrollmentrequest.repositories.EnrollmentRequestRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

class InMemoryEnrollmentRequestsRepository extends InMemoryDomainRepository<EnrollmentRequest, Integer>
        implements EnrollmentRequestRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryEnrollmentRequestsRepository() {

    }

    @Override
    public Iterable<Course> coursesOfEnrollmentRequestsByStudent(Student s) {
        return valuesStream()
                .filter(request -> request.student().sameAs(s))
                .map(EnrollmentRequest::course)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<EnrollmentRequest> pendingEnrollmentRequests() {
        return match(EnrollmentRequest::isPending);
    }

    @Override
    public Optional<EnrollmentRequest> findFirstPendingEnrollmentRequest() {
        return matchOne(EnrollmentRequest::isPending);
    }
}
