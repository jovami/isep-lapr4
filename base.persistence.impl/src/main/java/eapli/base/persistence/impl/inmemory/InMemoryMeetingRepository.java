package eapli.base.persistence.impl.inmemory;

import eapli.base.event.meeting.domain.Meeting;
import eapli.base.event.meeting.domain.MeetingState;
import eapli.base.event.meeting.repositories.MeetingRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;

class InMemoryMeetingRepository extends InMemoryDomainRepository<Meeting, Integer> implements MeetingRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryMeetingRepository() {
        super();
    }

    @Override
    public Iterable<Meeting> findAllMeetingsWithParticipantWithPendingStatus(SystemUser user) {
        // select all meetings with participant with pending status
        // TODO: add
        return null;
    }

    @Override
    public Iterable<Meeting> organizedBy(SystemUser user) {
        return match(admin -> user.sameAs(admin));
    }

    @Override
    public List<Meeting> meetingsOfAdminWithState(SystemUser systemUser, MeetingState state) {
        return null;
    }

}
