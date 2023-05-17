package eapli.base.persistence.impl.jpa;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.StaffMember;
import eapli.base.course.repositories.StaffRepository;

public class JpaStaffRepository extends BaseJpaRepositoryBase<StaffMember,Long,Integer> implements StaffRepository {
    JpaStaffRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, identityFieldName);
    }

    JpaStaffRepository(String identityFieldName) {
        super(identityFieldName);
    }

    @Override
    public Iterable<StaffMember> findByCourse(Course course) {
        return match("e.course=:course","course",course);
    }
}
