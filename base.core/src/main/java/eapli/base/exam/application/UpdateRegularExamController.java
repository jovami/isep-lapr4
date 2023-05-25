package eapli.base.exam.application;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.course.dto.CourseAndDescriptionDTO;
import eapli.base.course.dto.CourseAndDescriptionDTOMapper;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.exam.domain.regular_exam.RegularExam;
import eapli.base.exam.domain.regular_exam.RegularExamDate;
import eapli.base.exam.domain.regular_exam.RegularExamSpecification;
import eapli.base.exam.repositories.RegularExamRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class UpdateRegularExamController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RepositoryFactory repositoryFactory;
    private final RegularExamRepository repoRegularExam;

    private final StaffRepository repoStaff;

    private final TeacherRepository repoTeacher;

    private final CourseRepository repoCourse;

    public UpdateRegularExamController() {
        this.repositoryFactory = PersistenceContext.repositories();
        this.repoRegularExam = repositoryFactory.regularExams();
        this.repoStaff = repositoryFactory.staffs();
        this.repoTeacher = repositoryFactory.teachers();
        this.repoCourse = repositoryFactory.courses();
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
        var session = authz.session();
        if (session.isEmpty()) {
            throw new IllegalStateException("Session not found");
        }

        var teacher = repoTeacher.findBySystemUser(session.get().authenticatedUser()).orElseThrow(
                () -> new IllegalStateException("Teacher not found"));

        return getCourses(repoStaff.taughtBy(teacher));
    }

    public Iterable<RegularExam> listExams(CourseAndDescriptionDTO chosen) {
        var course = fromDTO(chosen);

        return repoRegularExam.findByCourse(course);
    }

    public void updateRegularExamDate(RegularExam regularExam, LocalDateTime openDate, LocalDateTime closeDate) {
        regularExam.updateRegularExamDate(RegularExamDate.valueOf(openDate, closeDate));
        repoRegularExam.save(regularExam);
    }

    public boolean updateRegularExamSpecification(RegularExam regularExam, File file) throws IOException {

        if (!new ValidateRegularExamSpecificationService().validate(file))
            return false;

        regularExam.updateRegularExamSpecification(RegularExamSpecification.valueOf(file));
        repoRegularExam.save(regularExam);

        return true;
    }

}
