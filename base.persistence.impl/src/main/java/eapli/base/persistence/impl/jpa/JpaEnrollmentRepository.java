package eapli.base.persistence.impl.jpa;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.enrollment.domain.Enrollment;
import eapli.base.enrollment.repositories.EnrollmentRepository;

public class JpaEnrollmentRepository extends BaseJpaRepositoryBase<Enrollment,Long,Integer> implements EnrollmentRepository {
    public JpaEnrollmentRepository(String persistenceUnitName) {
        super(persistenceUnitName, "code");
    }

    @Override
    public Iterable<Course> coursesOfEnrolledStudent(Student s) {
        final var query = entityManager().createQuery(
                "SELECT e.course FROM Enrollment e WHERE e.student = :student",
                Course.class);
        query.setParameter("student", s);
        return query.getResultList();
    }
}
