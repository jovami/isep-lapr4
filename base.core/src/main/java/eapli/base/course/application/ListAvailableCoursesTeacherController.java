package eapli.base.course.application;

import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.course.dto.AvailableCourseDTO;
import eapli.base.course.dto.AvailableCourseDTOMapper;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.List;

import static eapli.base.clientusermanagement.usermanagement.domain.BaseRoles.TEACHER;

public class ListAvailableCoursesTeacherController {
    private final AuthorizationService authz;
    private final TeacherRepository teacherRepo;
    private final StaffRepository staffRepo;
    private final ListCoursesService svc;

    public ListAvailableCoursesTeacherController() {
        var repos = PersistenceContext.repositories();
        this.authz = AuthzRegistry.authorizationService();

        this.teacherRepo = repos.teachers();
        this.staffRepo = repos.staffs();

        this.svc = new ListCoursesService();
    }

    /**
     * @return DTO list with the courses
     */
    public List<AvailableCourseDTO> availableCourses() throws IllegalStateException {
        if(!this.authz.isAuthenticatedUserAuthorizedTo(TEACHER)) {
            throw new IllegalStateException("User is not of an appropriate role");
        }

        var teacher = this.teacherRepo.findBySystemUser(getUser())
                .orElseThrow(() -> new IllegalStateException("User not registered as Teacher"));

        return new AvailableCourseDTOMapper().toDTO(this.staffRepo.taughtBy(teacher));

    }

    private SystemUser getUser() throws IllegalStateException {
        return this.authz.session()
                .orElseThrow(() -> new IllegalStateException("User not logged in"))
                .authenticatedUser();
    }
}
