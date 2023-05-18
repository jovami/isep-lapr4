package eapli.base.persistence.impl.jpa;

import eapli.base.event.Meeting.domain.Meeting;
import eapli.base.event.Meeting.repositories.MeetingRepository;

public class JpaMeetingRepository extends BaseJpaRepositoryBase<Meeting,Long,Integer> implements MeetingRepository {
    JpaMeetingRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, identityFieldName);
    }

    JpaMeetingRepository(String identityFieldName) {
        super(identityFieldName);
    }

}
