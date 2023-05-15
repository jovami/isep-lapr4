package eapli.base.persistence.impl.jpa;

import eapli.base.clientusermanagement.domain.users.Acronym;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 * JpaTeacherRepository
 */
public class JpaTeacherRepository extends BaseJpaRepositoryBase<Teacher, Long, Acronym> implements TeacherRepository {

    JpaTeacherRepository(String identityFieldName) {
        super(identityFieldName, "acronym");
    }

    @Override
    public Teacher findBySystemUser(Username username) {
        return (Teacher) match("systemUser.username=:username");
    }
}
