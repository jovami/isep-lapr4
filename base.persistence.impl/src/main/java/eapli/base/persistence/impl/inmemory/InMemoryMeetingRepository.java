package eapli.base.persistence.impl.inmemory;

import eapli.base.event.Meeting.domain.Meeting;
import eapli.base.event.Meeting.repositories.MeetingRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

class InMemoryMeetingRepository extends InMemoryDomainRepository<Meeting, Integer> implements MeetingRepository {

    static {
        InMemoryInitializer.init();
    }

    public InMemoryMeetingRepository() {
        super();
    }
}
