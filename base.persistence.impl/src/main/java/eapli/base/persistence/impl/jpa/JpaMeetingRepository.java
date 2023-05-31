package eapli.base.persistence.impl.jpa;

import eapli.base.event.meeting.domain.Meeting;
import eapli.base.event.meeting.domain.MeetingParticipantStatus;
import eapli.base.event.meeting.domain.MeetingState;
import eapli.base.event.meeting.repositories.MeetingRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.List;


class JpaMeetingRepository extends BaseJpaRepositoryBase<Meeting, Long, Integer> implements MeetingRepository {

    /*JpaMeetingRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, "meetingId");
    }*/

    JpaMeetingRepository(String identityFieldName) {
        super(identityFieldName,"meetingId");
    }
    public JpaMeetingRepository(final TransactionalContext autoTx) {
        super(autoTx.toString(), "meetingId");
    }

    @Override
    public Iterable<Meeting> findAllMeetingsWithParticipantWithPendingStatus(SystemUser user) {
        final var query = entityManager().createQuery(
                "SELECT mp.meeting FROM MeetingParticipant mp" +
                        " WHERE mp.user = :user AND mp.status = :status",
                Meeting.class);
        query.setParameter("user", user);
        query.setParameter("status", MeetingParticipantStatus.PENDING);
        return query.getResultList();
    }

    @Override
    public Iterable<Meeting> organizedBy(SystemUser user) {
        return match("e.meetingAdmin = :user", "user", user);
    }

    @Override
    public List<Meeting> meetingsOfAdminWithState(SystemUser systemUser, MeetingState state) {
        return match("e.meetingAdmin = :user AND e.state = :state","user",systemUser,"state", state);
    }
}
