package eapli.base.course.application;

import static eapli.base.clientusermanagement.usermanagement.domain.BaseRoles.MANAGER;
import static eapli.base.clientusermanagement.usermanagement.domain.BaseRoles.POWER_USER;

import eapli.base.course.domain.CourseFactory;
import eapli.base.course.dto.CreateCourseDTO;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public final class CreateCourseController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final CourseRepository repo;

    public CreateCourseController() {
        this.repo = PersistenceContext.repositories().courses();
    }

    public boolean createCourse(CreateCourseDTO dto) {
        this.authz.ensureAuthenticatedUserHasAnyOf(POWER_USER, MANAGER);
        var course = new CourseFactory().build(dto);
        return this.repo.save(course) != null;
    }
}
