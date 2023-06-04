package eapli.base.exam.application;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.exam.application.parser.RegularExamValidatorService;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.domain.regular_exam.RegularExamDate;
import eapli.base.exam.domain.regular_exam.RegularExamSpecification;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.infrastructure.grammar.GrammarContext;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public class CreateRegularExamController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RegularExamValidatorService svc;
    private final RepositoryFactory repositoryFactory;
    private final RegularExamRepository repoRegularExam;

    private final StaffRepository repoStaff;

    private final CourseRepository repoCourse;

    public CreateRegularExamController() {
        this.repositoryFactory = PersistenceContext.repositories();
        this.repoRegularExam = repositoryFactory.regularExams();
        this.repoStaff = repositoryFactory.staffs();
        this.repoCourse = repositoryFactory.courses();
        this.svc = GrammarContext.grammarTools().regularExamValidator();
    }

    public boolean createRegularExam(File file, LocalDateTime openDate, LocalDateTime closeDate, Course chosen) throws IOException {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        var course = this.repoCourse.ofIdentity(chosen.identity());

        if (!this.svc.validate(file))
            return false;

        var rexam = new RegularExam(RegularExamSpecification.valueOf(file),
                RegularExamDate.valueOf(openDate, closeDate), course.get());

        this.repoRegularExam.save(rexam);

        return true;
    }

    public Iterable<Course> listCoursesTeacherTeaches() {
        return repoStaff.taughtBy(new MyUserService().currentTeacher());
    }

}
