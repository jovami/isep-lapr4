package eapli.base.persistence.impl.jpa;

import eapli.base.event.Meeting.domain.Meeting;
import eapli.base.event.Meeting.domain.MeetingParticipant;
import eapli.base.event.Meeting.repositories.MeetingRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

class JpaMeetingRepository extends BaseJpaRepositoryBase<Meeting, Long, Integer> implements MeetingRepository {

    JpaMeetingRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, identityFieldName);
    }

    JpaMeetingRepository(String identityFieldName) {
        super(identityFieldName);
    }
    public JpaMeetingRepository(final TransactionalContext autoTx) {
        super(autoTx.toString(), "meetingId");
    }

}
