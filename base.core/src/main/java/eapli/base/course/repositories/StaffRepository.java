package eapli.base.course.repositories;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.StaffMember;
import eapli.framework.domain.repositories.DomainRepository;

public interface StaffRepository extends DomainRepository<Integer, StaffMember> {
    Iterable<Teacher> findByCourse(Course course);

    Iterable<Course> taughtBy(Teacher t);

    Iterable<Course> nonClosedAndTaughtBy(Teacher t);
}
