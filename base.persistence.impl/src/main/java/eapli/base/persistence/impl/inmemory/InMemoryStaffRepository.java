package eapli.base.persistence.impl.inmemory;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseState;
import eapli.base.course.domain.StaffMember;
import eapli.base.course.repositories.StaffRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

class InMemoryStaffRepository extends InMemoryDomainRepository<StaffMember, Integer> implements StaffRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryStaffRepository() {
        super();
    }

    @Override
    public Iterable<Teacher> findByCourse(Course course) {
        return valuesStream()
                .filter(staff -> staff.course().sameAs(course))
                .map(StaffMember::member)
                .collect(Collectors.toList());
    }

    private Stream<Course> streamOfTaughtBy(Teacher t) {
        return valuesStream()
                .filter(staff -> staff.member().sameAs(t))
                .map(StaffMember::course);
    }

    @Override
    public Iterable<Course> taughtBy(Teacher t) {
        return streamOfTaughtBy(t).collect(Collectors.toList());
    }

    @Override
    public Iterable<Course> nonClosedAndTaughtBy(Teacher t) {
        return streamOfTaughtBy(t)
                .filter(course -> course.state() != CourseState.CLOSED)
                .collect(Collectors.toList());
    }
}
