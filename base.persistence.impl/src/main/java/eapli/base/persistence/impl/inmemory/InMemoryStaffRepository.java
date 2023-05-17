package eapli.base.persistence.impl.inmemory;

import java.util.stream.Collectors;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.StaffMember;
import eapli.base.course.repositories.StaffRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryStaffRepository extends InMemoryDomainRepository<StaffMember,Integer> implements StaffRepository {

    static {
         InMemoryInitializer.init();
    }

    public InMemoryStaffRepository() {
        super();
    }

    @Override
    public Iterable<StaffMember> findByCourse(Course course) {
        return match((staffMember -> course.equals(staffMember.course())));
    }

    @Override
    public Iterable<Course> taughtBy(Teacher t) {
        return valuesStream()
            .filter(staff -> staff.member().sameAs(t))
            .map(StaffMember::course)
            .collect(Collectors.toList());
    }
}
