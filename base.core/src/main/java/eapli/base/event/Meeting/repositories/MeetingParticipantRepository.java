package eapli.base.event.Meeting.repositories;

import eapli.base.event.Meeting.domain.Meeting;
import eapli.base.event.Meeting.domain.MeetingParticipant;
import eapli.framework.domain.repositories.DomainRepository;

public interface MeetingParticipantRepository extends DomainRepository<Integer, MeetingParticipant> {

    Iterable<MeetingParticipant> meetingParticipants(Meeting meeting);

}
