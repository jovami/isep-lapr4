package eapli.base.event.lecture.repositories;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.event.lecture.domain.Lecture;
import eapli.framework.domain.repositories.DomainRepository;

public interface LectureRepository extends DomainRepository<Integer, Lecture> {

    public Iterable<Lecture> lectureGivenBy(Teacher t) ;

}
