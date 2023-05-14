package eapli.base.persistence.impl.jpa;

import eapli.base.clientusermanagement.domain.users.Acronym;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.TeacherRepository;

/**
 * JpaTeacherRepository
 */
public class JpaTeacherRepository extends BaseJpaRepositoryBase<Teacher, Long, Acronym> implements TeacherRepository {

    JpaTeacherRepository(String identityFieldName) {
        super(identityFieldName, "acronym");
    }
}
