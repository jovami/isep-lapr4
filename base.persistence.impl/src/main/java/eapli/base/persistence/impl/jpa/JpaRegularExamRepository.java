package eapli.base.persistence.impl.jpa;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.StaffMember;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.repositories.RegularExamRepository;

public class JpaRegularExamRepository extends BaseJpaRepositoryBase<RegularExam,Long,Integer> implements RegularExamRepository {
    JpaRegularExamRepository(String persistenceUnitName){
        super(persistenceUnitName,"examid");
    }

    @Override
    public Iterable<RegularExam> findByCourse(Course course) {
        return match("e.course=:course","course",course);
    }

    //TODO: implement
}
