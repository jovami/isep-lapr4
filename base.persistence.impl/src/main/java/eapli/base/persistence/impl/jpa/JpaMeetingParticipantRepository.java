package eapli.base.persistence.impl.jpa;

import eapli.base.event.Meeting.domain.Meeting;
import eapli.base.event.Meeting.domain.MeetingParticipant;
import eapli.base.event.Meeting.repositories.MeetingParticipantRepository;

public class JpaMeetingParticipantRepository extends BaseJpaRepositoryBase<MeetingParticipant,Long,Integer> implements MeetingParticipantRepository {
    JpaMeetingParticipantRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, identityFieldName);
    }

    JpaMeetingParticipantRepository(String identityFieldName) {
        super(identityFieldName);
    }

    @Override
    public Iterable<MeetingParticipant> meetingParticipants(Meeting meeting) {
        return match("e.meeting=:meeting","meeting",meeting);
    }
}
