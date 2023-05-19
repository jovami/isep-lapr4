package eapli.base.persistence.impl.inmemory;

import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.repositories.LectureRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryLectureRepository extends InMemoryDomainRepository<Lecture,Integer> implements LectureRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryLectureRepository() {
        super();
    }
}
