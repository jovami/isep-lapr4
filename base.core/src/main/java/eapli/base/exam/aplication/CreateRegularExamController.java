package eapli.base.exam.aplication;


import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.course.domain.Course;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamDate;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamSpecification;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;


public class CreateRegularExamController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RepositoryFactory repositoryFactory;
    private final RegularExamRepository repoRegularExam;

    private final StaffRepository repoStaff;

    private final TeacherRepository repoTeacher;

    private final CourseRepository repoCourse;


    public CreateRegularExamController()
    {
        this.repositoryFactory = PersistenceContext.repositories();
        this.repoRegularExam = repositoryFactory.regularExams();
        this.repoStaff = repositoryFactory.staffs();
        this.repoTeacher = repositoryFactory.teachers();
        this.repoCourse = repositoryFactory.courses();
    }


    public boolean createRegularExam(File file, Date openDate, Date closeDate,Course chosen) throws IOException
    {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,BaseRoles.TEACHER);
        var course = this.repoCourse.ofIdentity(chosen.identity());

        if (!new ValidateRegularExamSpecificationService().validate(file))
            return false;

        var rexam = new RegularExam(RegularExamSpecification.valueOf(file), RegularExamDate.valueOf(openDate,closeDate),chosen);

        this.repoRegularExam.save(rexam);

        return true;
    }

    public Iterable<Course> listCoursesTeacherTeaches()
    {
        SystemUser userTeacher = authz.session().get().authenticatedUser();
        Optional<Teacher> teacher = repoTeacher.findBySystemUser(userTeacher);

        return repoStaff.taughtBy(teacher.get());
    }

}
