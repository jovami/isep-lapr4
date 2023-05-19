package eapli.base.event.lecture.repositories;

import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.domain.LectureParticipant;
import eapli.framework.domain.repositories.DomainRepository;

public interface LectureParticipantRepository extends DomainRepository<Integer, LectureParticipant> {

    Iterable<LectureParticipant> lectureParticipants(Lecture lecture);

}
