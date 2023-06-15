package eapli.base.exam.application;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.exam.domain.RegularExamFactory;
import eapli.base.exam.domain.RegularExamTitle;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.infrastructure.grammar.GrammarContext;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public class CreateRegularExamController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RepositoryFactory repositoryFactory;
    private final RegularExamRepository repoRegularExam;

    private final StaffRepository repoStaff;

    private final CourseRepository repoCourse;

    public CreateRegularExamController() {
        this.repositoryFactory = PersistenceContext.repositories();
        this.repoRegularExam = repositoryFactory.regularExams();
        this.repoStaff = repositoryFactory.staffs();
        this.repoCourse = repositoryFactory.courses();
    }

    public boolean createRegularExam(File file, String title, LocalDateTime openDate, LocalDateTime closeDate, Course chosen) throws IOException {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.TEACHER);
        var course = this.repoCourse.ofIdentity(chosen.identity())
                .orElseThrow(() -> new ConcurrencyException("Course no longer exists"));

        var validator = GrammarContext.grammarTools().regularExamValidator();
        var exam = new RegularExamFactory(validator).build(title,
                openDate, closeDate, course, file);

        exam.ifPresent(this.repoRegularExam::save);
        return exam.isPresent();
    }

    public Iterable<Course> listCoursesTeacherTeaches() {
        return repoStaff.taughtBy(new MyUserService().currentTeacher());
    }

}
