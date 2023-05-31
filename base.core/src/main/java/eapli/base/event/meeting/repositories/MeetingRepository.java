package eapli.base.event.meeting.repositories;

import eapli.base.event.meeting.domain.Meeting;
import eapli.base.event.meeting.domain.MeetingState;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.List;

public interface MeetingRepository extends DomainRepository<Integer, Meeting> {
    Iterable<Meeting> findAllMeetingsWithParticipantWithPendingStatus(SystemUser user);

    Iterable<Meeting> organizedBy(SystemUser user);
    List<Meeting> meetingsOfAdminWithState(SystemUser systemUser, MeetingState state);
}
