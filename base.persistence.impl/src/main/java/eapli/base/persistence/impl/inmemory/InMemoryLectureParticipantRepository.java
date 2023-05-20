package eapli.base.persistence.impl.inmemory;

import java.util.stream.Collectors;

import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.domain.LectureParticipant;
import eapli.base.event.lecture.repositories.LectureParticipantRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

class InMemoryLectureParticipantRepository extends InMemoryDomainRepository<LectureParticipant, Integer>
        implements LectureParticipantRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryLectureParticipantRepository() {
        super();
    }

    @Override
    public Iterable<LectureParticipant> lectureParticipants(Lecture lecture) {
        return valuesStream()
                .filter(lectureParticipant -> lectureParticipant.lecture().sameAs(lecture))
                .collect(Collectors.toList());
    }
}
