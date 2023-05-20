package eapli.base.persistence.impl.inmemory;

import java.util.stream.Collectors;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.enrollment.domain.Enrollment;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

class InMemoryEnrollmentRepository extends InMemoryDomainRepository<Enrollment, Integer> implements EnrollmentRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryEnrollmentRepository() {

    }

    @Override
    public Iterable<Course> coursesOfEnrolledStudent(Student s) {
        return valuesStream()
            .filter(enr -> enr.student().sameAs(s))
            .map(Enrollment::course)
            .collect(Collectors.toList());
    }

    @Override
    public Iterable<Enrollment> enrollmentsByCourse(Course c) {
        return valuesStream()
            .filter(enr -> enr.course().sameAs(c))
            .collect(Collectors.toList());
    }
}
