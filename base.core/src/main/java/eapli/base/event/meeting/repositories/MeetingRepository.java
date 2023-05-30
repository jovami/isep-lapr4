package eapli.base.event.meeting.repositories;

import eapli.base.event.meeting.domain.Meeting;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

public interface MeetingRepository extends DomainRepository<Integer, Meeting> {
    Iterable<Meeting> findAllMeetingsWithParticipantWithPendingStatus(SystemUser user);

}
