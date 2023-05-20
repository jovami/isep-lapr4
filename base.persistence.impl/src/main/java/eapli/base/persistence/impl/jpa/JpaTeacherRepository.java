package eapli.base.persistence.impl.jpa;

import java.util.Optional;

import eapli.base.clientusermanagement.domain.users.Acronym;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 * JpaTeacherRepository
 */
class JpaTeacherRepository extends BaseJpaRepositoryBase<Teacher, Long, Acronym> implements TeacherRepository {

    JpaTeacherRepository(String identityFieldName) {
        super(identityFieldName, "acronym");
    }

    @Override
    public Teacher findBySystemUser(Username username) {
        return (Teacher) match("systemUser.username=:username");
    }

    @Override
    public Optional<Teacher> findBySystemUser(SystemUser user) {
        return matchOne("e.systemUser = :user", "user", user);
    }
}
