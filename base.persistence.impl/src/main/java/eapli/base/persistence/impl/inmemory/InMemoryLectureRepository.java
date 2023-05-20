package eapli.base.persistence.impl.inmemory;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.StaffMember;
import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.repositories.LectureRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryLectureRepository extends InMemoryDomainRepository<Lecture,Integer> implements LectureRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryLectureRepository() {
        super();
    }

    @Override
    public Iterable<Lecture> lectureGivenBy(Teacher t) {
        return valuesStream()
                .filter(lecture -> lecture.teacher().sameAs(t))
                .collect(Collectors.toList());
    }







}
