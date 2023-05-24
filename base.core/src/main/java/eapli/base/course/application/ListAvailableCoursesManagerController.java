package eapli.base.course.application;

import eapli.base.course.dto.AvailableCourseDTO;
import eapli.base.course.dto.AvailableCourseDTOMapper;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.List;

import static eapli.base.clientusermanagement.usermanagement.domain.BaseRoles.*;

public class ListAvailableCoursesManagerController {
    private final AuthorizationService authz;
    private final CourseRepository courseRepo;
    private final ListCoursesService svc;

    public ListAvailableCoursesManagerController() {
        var repos = PersistenceContext.repositories();
        this.authz = AuthzRegistry.authorizationService();

        this.courseRepo = repos.courses();

        this.svc = new ListCoursesService();
    }

    /**
     * @return DTO list with the courses
     */
    public List<AvailableCourseDTO> availableCourses() throws IllegalStateException {
        if(!this.authz.isAuthenticatedUserAuthorizedTo(POWER_USER, MANAGER)) {
            throw new IllegalStateException("User is not of an appropriate role");
        }

        return new AvailableCourseDTOMapper().toDTO(this.courseRepo.findAll());
    }

    private SystemUser getUser() throws IllegalStateException {
        return this.authz.session()
                .orElseThrow(() -> new IllegalStateException("User not logged in"))
                .authenticatedUser();
    }
}
