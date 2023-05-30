package eapli.base.event.meeting.repositories;

import eapli.base.event.meeting.domain.Meeting;
import eapli.base.event.meeting.domain.MeetingParticipant;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.List;
import java.util.Optional;

public interface MeetingParticipantRepository extends DomainRepository<Integer, MeetingParticipant> {

    Iterable<MeetingParticipant> meetingParticipants(Meeting meeting);

    List<MeetingParticipant> findMeetingParticipantByUser(SystemUser user);

    Optional<MeetingParticipant> findMeetingParticipantByUserAndMeeting(SystemUser systemUser, Meeting meeting);


    Iterable<Meeting> meetingsOfUser(SystemUser user);
}
