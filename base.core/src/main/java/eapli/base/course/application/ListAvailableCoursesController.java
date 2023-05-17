package eapli.base.course.application;

import static eapli.base.clientusermanagement.usermanagement.domain.BaseRoles.*;

import java.util.List;

import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.course.dto.AvailableCourseDTO;
import eapli.base.course.dto.AvailableCourseDTOMapper;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

/**
 * ListAvailableCoursesController
 */
@UseCaseController
public final class ListAvailableCoursesController {
    private final CourseRepository courseRepo;

    private final StudentRepository studentRepo;

    private final StaffRepository staffRepo;
    private final TeacherRepository teacherRepo;

    private final ListCoursesService svc;
    private final AuthorizationService authz;

    public ListAvailableCoursesController() {
        var repos = PersistenceContext.repositories();
        this.authz = AuthzRegistry.authorizationService();

        this.courseRepo = repos.courses();

        this.studentRepo = repos.students();

        this.staffRepo = repos.staffs();
        this.teacherRepo = repos.teachers();

        this.svc = new ListCoursesService();
    }

    /**
     * Lists all available courses according to the current user's Role
     *
     * @return DTO list with the courses
     */
    public List<AvailableCourseDTO> availableCourses() throws IllegalStateException {
        if (hasRoles(POWER_USER, MANAGER)) {
            return this.manager();
        } else if (hasRoles(TEACHER)) {
            return this.teacher();
        } else if (hasRoles(STUDENT)) {
            return this.student();
        }

        throw new IllegalStateException("User is not of an appropriate role");
    }

    private List<AvailableCourseDTO> student() {
        var student = this.studentRepo.findBySystemUser(getUser())
                .orElseThrow(() -> new IllegalStateException("User not registered as Student"));


        return new AvailableCourseDTOMapper().toDTO(this.svc.studentIsEnrollableOrCanEnroll(student));
    }

    private List<AvailableCourseDTO> teacher() {
        var teacher = this.teacherRepo.findBySystemUser(getUser())
                .orElseThrow(() -> new IllegalStateException("User not registered as Teacher"));

        return new AvailableCourseDTOMapper().toDTO(this.staffRepo.taughtBy(teacher));
    }

    /**
     * Lists all existing courses in the system
     *
     * @apiNote Only managers (and power users) can execute this
     */
    private List<AvailableCourseDTO> manager() {
        return new AvailableCourseDTOMapper().toDTO(this.courseRepo.findAll());
    }

    // Helper methods to avoid duplication
    private boolean hasRoles(Role... roles) {
        return this.authz.isAuthenticatedUserAuthorizedTo(roles);
    }

    private SystemUser getUser() throws IllegalStateException {
        return this.authz.session()
                .orElseThrow(() -> new IllegalStateException("User not logged in"))
                .authenticatedUser();
    }
}
