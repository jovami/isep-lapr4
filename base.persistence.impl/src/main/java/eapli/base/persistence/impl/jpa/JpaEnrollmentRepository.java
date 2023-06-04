package eapli.base.persistence.impl.jpa;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.course.domain.Course;
import eapli.base.enrollment.domain.Enrollment;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.framework.domain.repositories.TransactionalContext;

class JpaEnrollmentRepository extends BaseJpaRepositoryBase<Enrollment, Long, Integer>
        implements EnrollmentRepository {

    public JpaEnrollmentRepository(final TransactionalContext autoTx) {
        super(autoTx.toString(), "code");
    }

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

    @Override
    public Iterable<Enrollment> enrollmentsByCourse(Course c) {
        return match("e.course=:course", "course", c);
    }

    @Override
    public Iterable<Student> studentsOfEnrolledCourse(Course c) {
        final var query = entityManager().createQuery(
                "SELECT e.student FROM Enrollment e WHERE e.course = :course",
                Student.class);
        query.setParameter("course", c);
        return query.getResultList();
    }

    @Override
    public Iterable<Course> ongoingCoursesOfStudent(Student s) {
        final var query = entityManager().createQuery(
                "SELECT e.course FROM Enrollment e WHERE e.student = :student AND e.course.state = 'INPROGRESS'",
                Course.class);
        query.setParameter("student", s);
        return query.getResultList();
    }
}