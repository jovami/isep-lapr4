package eapli.base.persistence.impl.jpa;

import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.domain.LectureParticipant;
import eapli.base.event.lecture.repositories.LectureParticipantRepository;

class JpaLectureParticipantRepository extends BaseJpaRepositoryBase<LectureParticipant, Long, Integer>
        implements LectureParticipantRepository {

    JpaLectureParticipantRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, identityFieldName);
    }

    JpaLectureParticipantRepository(String identityFieldName) {
        super(identityFieldName);
    }

    @Override
    public Iterable<LectureParticipant> lectureParticipants(Lecture lecture) {
        return match("e.lecture=:lecture", "lecture", lecture);
    }
}
