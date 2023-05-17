package eapli.base.course.repositories;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.StaffMember;
import eapli.framework.domain.repositories.DomainRepository;


public interface StaffRepository extends DomainRepository<Integer, StaffMember> {
    Iterable<StaffMember> findByCourse(Course course);
}
