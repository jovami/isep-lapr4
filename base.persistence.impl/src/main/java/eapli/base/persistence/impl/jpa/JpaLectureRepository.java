package eapli.base.persistence.impl.jpa;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.repositories.LectureRepository;

class JpaLectureRepository extends BaseJpaRepositoryBase<Lecture, Long, Integer> implements LectureRepository {

    JpaLectureRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, identityFieldName);
    }

    JpaLectureRepository(String identityFieldName) {
        super(identityFieldName);
    }

    @Override
    public Iterable<Lecture> lectureGivenBy(Teacher t) {
        final var query = entityManager().createQuery(
                "SELECT sm FROM Lecture sm WHERE sm.teacher = :teacher",
                Lecture.class);
        query.setParameter("teacher", t);
        return query.getResultList();
    }
}
