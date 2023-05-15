package eapli.base.persistence.impl.jpa;

import eapli.base.clientusermanagement.domain.users.Manager;
import eapli.base.clientusermanagement.domain.users.ManagerId;
import eapli.base.clientusermanagement.repositories.ManagerRepository;

/**
 * JpaManagerRepository
 */
public class JpaManagerRepository extends BaseJpaRepositoryBase<Manager, Long, Integer> implements ManagerRepository {

    JpaManagerRepository(String identityFieldName) {
        super(identityFieldName, "managerId");
    }
}
