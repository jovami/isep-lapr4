package eapli.base.persistence.impl.jpa;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.StaffMember;
import eapli.base.course.repositories.StaffRepository;

public class JpaStaffRepository extends BaseJpaRepositoryBase<StaffMember,Long,Integer> implements StaffRepository {
    JpaStaffRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, identityFieldName);
    }

    JpaStaffRepository(String identityFieldName) {
        super(identityFieldName, "StaffMemberId");
    }

    @Override
    public Iterable<StaffMember> findByCourse(Course course) {
        return match("e.course=:course","course",course);
    }

    @Override
    public Iterable<Course> taughtBy(Teacher t) {
        final var query = entityManager().createQuery(
                "SELECT sm.course FROM StaffMember sm WHERE sm.member = :teacher",
                Course.class);
        query.setParameter("teacher", t);
        return query.getResultList();
    }
}
