package eapli.base.event.meeting.application;

import static org.eclipse.collections.impl.block.factory.HashingStrategies.fromFunction;

import org.eclipse.collections.impl.factory.HashingStrategySets;

import eapli.base.event.meeting.domain.Meeting;
import eapli.base.event.meeting.repositories.MeetingParticipantRepository;
import eapli.base.event.meeting.repositories.MeetingRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

/**
 * ListMeetingsService
 */
public final class ListMeetingsService {

    private final MeetingRepository meetingRepo;
    private final MeetingParticipantRepository participantRepo;

    public ListMeetingsService() {
        var repos = PersistenceContext.repositories();

        this.meetingRepo = repos.meetings();
        this.participantRepo = repos.meetingParticipants();
    }

    public Iterable<Meeting> meetingsOfUser(SystemUser user) {
        var meetings = HashingStrategySets.mutable.withAll(
                fromFunction(Meeting::identity),
                this.meetingRepo.organizedBy(user));

        meetings.addAllIterable(this.participantRepo.meetingsOfUser(user));
        return meetings;
    }
}
