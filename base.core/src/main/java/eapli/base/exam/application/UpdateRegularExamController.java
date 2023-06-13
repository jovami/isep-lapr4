package eapli.base.exam.application;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.course.dto.CourseAndDescriptionDTO;
import eapli.base.course.dto.CourseAndDescriptionDTOMapper;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.exam.application.parser.RegularExamValidatorService;
import eapli.base.exam.domain.RegularExam;
import eapli.base.exam.domain.RegularExamDate;
import eapli.base.exam.domain.RegularExamFactory;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.infrastructure.grammar.GrammarContext;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public class UpdateRegularExamController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RegularExamValidatorService svc;
    private final RepositoryFactory repositoryFactory;
    private final RegularExamRepository repoRegularExam;

    private final StaffRepository repoStaff;

    private final CourseRepository repoCourse;

    public UpdateRegularExamController() {
        this.repositoryFactory = PersistenceContext.repositories();
        this.repoRegularExam = repositoryFactory.regularExams();
        this.repoStaff = repositoryFactory.staffs();
        this.repoCourse = repositoryFactory.courses();
        this.svc = GrammarContext.grammarTools().regularExamValidator();
    }

    private List<CourseAndDescriptionDTO> getCourses(Iterable<Course> courses) {
        return new CourseAndDescriptionDTOMapper().toDTO(courses, Comparator.comparing(Course::identity));
    }

    private Course fromDTO(CourseAndDescriptionDTO dto) throws ConcurrencyException {
        return this.repoCourse.ofIdentity(dto.courseId())
                .orElseThrow(() -> new ConcurrencyException("Course no longer exists"));
    }

    public List<CourseAndDescriptionDTO> listCourses() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.TEACHER);
        return getCourses(repoStaff.taughtBy(new MyUserService().currentTeacher()));
    }

    public Iterable<RegularExam> listExams(CourseAndDescriptionDTO chosen) {
        var course = fromDTO(chosen);

        return repoRegularExam.findByCourse(course);
    }

    public void updateRegularExamDate(RegularExam regularExam, LocalDateTime openDate, LocalDateTime closeDate) {
        regularExam.updateDate(RegularExamDate.valueOf(openDate, closeDate));
        repoRegularExam.save(regularExam);
    }

    public boolean updateRegularExamSpecification(RegularExam regularExam, File file) throws IOException {
        var exam = new RegularExamFactory(this.svc).updateExamSpecification(regularExam, file);
        exam.ifPresent(repoRegularExam::save);
        return exam.isPresent();
    }

}
