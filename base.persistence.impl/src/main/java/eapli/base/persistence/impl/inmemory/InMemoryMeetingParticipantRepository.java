package eapli.base.persistence.impl.inmemory;

import java.util.stream.Collectors;

import eapli.base.event.Meeting.domain.Meeting;
import eapli.base.event.Meeting.domain.MeetingParticipant;
import eapli.base.event.Meeting.repositories.MeetingParticipantRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

class InMemoryMeetingParticipantRepository extends InMemoryDomainRepository<MeetingParticipant, Integer>
        implements MeetingParticipantRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryMeetingParticipantRepository() {
        super();
    }

    @Override
    public Iterable<MeetingParticipant> meetingParticipants(Meeting meeting) {
        return valuesStream()
                .filter(meetingParticipant -> meetingParticipant.meeting().sameAs(meeting))
                .collect(Collectors.toList());
    }
}
