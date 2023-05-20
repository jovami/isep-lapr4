package eapli.base.persistence.impl.jpa;

import eapli.base.event.timetable.domain.TimeTable;
import eapli.base.event.timetable.repositories.TimeTableRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

class JpaTimeTableRepository extends BaseJpaRepositoryBase<TimeTable, Long, Integer>
        implements TimeTableRepository {

    JpaTimeTableRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, identityFieldName);
    }

    JpaTimeTableRepository(String identityFieldName) {
        super(identityFieldName);
    }

    @Override
    public Iterable<TimeTable> findBySystemUser(SystemUser user) {
        return match("e.user=:user", "user", user);
    }
}
