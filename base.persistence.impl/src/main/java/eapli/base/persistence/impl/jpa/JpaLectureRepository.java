package eapli.base.persistence.impl.jpa;


import eapli.base.event.lecture.domain.Lecture;
import eapli.base.event.lecture.repositories.LectureRepository;

public class JpaLectureRepository extends BaseJpaRepositoryBase<Lecture,Long,Integer> implements LectureRepository {
    JpaLectureRepository(String persistenceUnitName, String identityFieldName) {
        super(persistenceUnitName, identityFieldName);
    }

    JpaLectureRepository(String identityFieldName) {
        super(identityFieldName);
    }

}
