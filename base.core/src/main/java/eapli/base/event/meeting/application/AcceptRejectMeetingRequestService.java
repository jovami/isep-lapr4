package eapli.base.event.meeting.application;

import eapli.base.event.meeting.domain.MeetingParticipant;
import eapli.base.event.meeting.repositories.MeetingParticipantRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

public class AcceptRejectMeetingRequestService {

    MeetingParticipantRepository repository;

    public AcceptRejectMeetingRequestService() {
        this.repository = PersistenceContext.repositories().meetingParticipants();
    }

    public boolean acceptMeetingRequest(MeetingParticipant participant) {
        participant.accept();
        return repository.save(participant) != null;
    }

    public boolean rejectMeetingRequest(MeetingParticipant participant) {
        participant.reject();
        return repository.save(participant) != null;
    }

}
