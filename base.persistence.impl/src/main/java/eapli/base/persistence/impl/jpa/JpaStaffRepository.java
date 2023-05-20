package eapli.base.persistence.impl.jpa;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseState;
import eapli.base.course.domain.StaffMember;
import eapli.base.course.repositories.StaffRepository;

class JpaStaffRepository extends BaseJpaRepositoryBase<StaffMember, Long, Integer> implements StaffRepository {
    JpaStaffRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, identityFieldName);
    }

    JpaStaffRepository(String identityFieldName) {
        super(identityFieldName, "StaffMemberId");
    }

    @Override
    public Iterable<Teacher> findByCourse(Course course) {
        final var query = entityManager().createQuery(
                "SELECT sm.member FROM StaffMember sm WHERE sm.course = :course",
                Teacher.class);
        query.setParameter("course", course);
        return query.getResultList();
    }

    @Override
    public Iterable<Course> taughtBy(Teacher t) {
        final var query = entityManager().createQuery(
                "SELECT sm.course FROM StaffMember sm WHERE sm.member = :teacher",
                Course.class);
        query.setParameter("teacher", t);
        return query.getResultList();
    }

    @Override
    public Iterable<Course> nonClosedAndTaughtBy(Teacher t) {
        final var query = entityManager().createQuery(
                "SELECT sm.course FROM StaffMember sm WHERE sm.member = :teacher AND sm.course.state <> :state",
                Course.class);
        query.setParameter("teacher", t);
        query.setParameter("state", CourseState.CLOSED);
        return query.getResultList();
    }
}
