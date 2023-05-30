package eapli.base.persistence.impl.jpa;

import eapli.base.event.meeting.domain.Meeting;
import eapli.base.event.meeting.domain.MeetingParticipant;
import eapli.base.event.meeting.repositories.MeetingParticipantRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.List;
import java.util.Optional;

class JpaMeetingParticipantRepository extends BaseJpaRepositoryBase<MeetingParticipant, Long, Integer>
        implements MeetingParticipantRepository {

    JpaMeetingParticipantRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, identityFieldName);
    }

    JpaMeetingParticipantRepository(String identityFieldName) {
        super(identityFieldName);
    }

    @Override
    public Iterable<MeetingParticipant> meetingParticipants(Meeting meeting) {
        return match("e.meeting=:meeting", "meeting", meeting);
    }

    @Override
    public List<MeetingParticipant> findMeetingParticipantByUser(SystemUser user) {
        return match("e.user=:user", "user", user);
    }

    @Override
    public Optional<MeetingParticipant> findMeetingParticipantByUserAndMeeting(SystemUser systemUser, Meeting meeting) {
        return matchOne("e.user=:user AND e.meeting=:meeting", "user", systemUser, "meeting", meeting);
    }

    @Override
    public Iterable<Meeting> meetingsOfUser(SystemUser user) {
        var query = entityManager().createQuery(
                "SELECT mp.meeting FROM MeetingParticipant mp "
                        + "WHERE mp.user = :user",
                Meeting.class);
        query.setParameter("user", user);
        return query.getResultList();
    }
}
