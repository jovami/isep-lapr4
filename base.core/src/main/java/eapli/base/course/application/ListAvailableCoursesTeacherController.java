package eapli.base.course.application;

import static eapli.base.clientusermanagement.usermanagement.domain.BaseRoles.TEACHER;

import java.util.List;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.course.dto.AvailableCourseDTO;
import eapli.base.course.dto.AvailableCourseDTOMapper;
import eapli.base.course.repositories.StaffRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public final class ListAvailableCoursesTeacherController {
    private final AuthorizationService authz;
    private final StaffRepository staffRepo;

    public ListAvailableCoursesTeacherController() {
        var repos = PersistenceContext.repositories();

        this.authz = AuthzRegistry.authorizationService();
        this.staffRepo = repos.staffs();
    }

    /**
     * @return DTO list with the courses
     */
    public List<AvailableCourseDTO> availableCourses() throws IllegalStateException {
        this.authz.ensureAuthenticatedUserHasAnyOf(TEACHER);

        var teacher = new MyUserService().currentTeacher();
        return new AvailableCourseDTOMapper().toDTO(this.staffRepo.taughtBy(teacher));
    }
}
