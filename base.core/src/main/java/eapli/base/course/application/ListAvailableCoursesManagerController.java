package eapli.base.course.application;

import static eapli.base.clientusermanagement.usermanagement.domain.BaseRoles.MANAGER;
import static eapli.base.clientusermanagement.usermanagement.domain.BaseRoles.POWER_USER;

import java.util.List;

import eapli.base.course.dto.AvailableCourseDTO;
import eapli.base.course.dto.AvailableCourseDTOMapper;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListAvailableCoursesManagerController {
    private final AuthorizationService authz;
    private final CourseRepository courseRepo;

    public ListAvailableCoursesManagerController() {
        this.authz = AuthzRegistry.authorizationService();
        this.courseRepo = PersistenceContext.repositories().courses();
    }

    /**
     * @return DTO list with the courses
     */
    public List<AvailableCourseDTO> availableCourses() throws IllegalStateException {
        this.authz.ensureAuthenticatedUserHasAnyOf(POWER_USER, MANAGER);
        return new AvailableCourseDTOMapper().toDTO(this.courseRepo.findAll());
    }
}
