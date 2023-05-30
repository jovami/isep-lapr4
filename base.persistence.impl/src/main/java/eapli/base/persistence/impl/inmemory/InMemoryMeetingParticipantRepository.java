package eapli.base.persistence.impl.inmemory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import eapli.base.event.meeting.domain.Meeting;
import eapli.base.event.meeting.domain.MeetingParticipant;
import eapli.base.event.meeting.repositories.MeetingParticipantRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
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

    @Override
    public List<MeetingParticipant> findMeetingParticipantByUser(SystemUser user) {
        return valuesStream()
                .filter(meetingParticipant -> meetingParticipant.sameAs(user)).collect(Collectors.toList());
    }

    @Override
    public Optional<MeetingParticipant> findMeetingParticipantByUserAndMeeting(SystemUser systemUser, Meeting meeting) {
        return valuesStream()
                .filter(meetingParticipant -> meetingParticipant.sameAs(systemUser)
                        && meetingParticipant.meeting().sameAs(meeting))
                .findFirst();
    }

    @Override
    public Iterable<Meeting> meetingsOfUser(SystemUser user) {
        return valuesStream()
                .filter(mp -> mp.participant().sameAs(user))
                .map(MeetingParticipant::meeting)
                .collect(Collectors.toList());
    }

}
