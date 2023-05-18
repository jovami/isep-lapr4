package eapli.base.persistence.impl.jpa;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.StaffMember;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.repositories.RegularExamRepository;

public class JpaRegularExamRepository extends BaseJpaRepositoryBase<RegularExam,Long,Integer> implements RegularExamRepository {
    JpaRegularExamRepository(String persistenceUnitName){
        super(persistenceUnitName,"IDREGULAREXAM");
    }

    @Override
    public Iterable<RegularExam> findByCourse(Course course) {
        final var query = entityManager().createQuery(
                "SELECT e FROM RegularExam e WHERE e.course = :course",
                RegularExam.class);
        query.setParameter("course", course);
        return query.getResultList();
    }

    //TODO: implement
}
