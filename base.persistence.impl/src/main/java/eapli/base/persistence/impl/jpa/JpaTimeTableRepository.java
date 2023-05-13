package eapli.base.persistence.impl.jpa;

import eapli.base.event.timetable.domain.TimeTable;
import eapli.base.event.timetable.repositories.TimeTableRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;

public class JpaTimeTableRepository extends BaseJpaRepositoryBase<TimeTable, Long, Username> implements TimeTableRepository {

    JpaTimeTableRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, identityFieldName);
    }

    JpaTimeTableRepository(String identityFieldName) {
        super(identityFieldName);
    }
}
