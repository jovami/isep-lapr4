package eapli.base.persistence.impl.jpa;

import eapli.base.clientusermanagement.domain.users.Manager;
import eapli.base.clientusermanagement.repositories.ManagerRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;

import java.util.Optional;

/**
 * JpaManagerRepository
 */
class JpaManagerRepository extends BaseJpaRepositoryBase<Manager, Long, Integer> implements ManagerRepository {

    JpaManagerRepository(String identityFieldName) {
        super(identityFieldName, "managerId");
    }

    @Override
    public Optional<Manager> findByUsername(Username username) {
        return matchOne("e.systemUser.username = :username", "username", username);
    }
}
