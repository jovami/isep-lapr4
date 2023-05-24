package eapli.base.course.application;

import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.course.dto.AvailableCourseDTO;
import eapli.base.course.dto.AvailableCourseDTOMapper;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.List;

import static eapli.base.clientusermanagement.usermanagement.domain.BaseRoles.STUDENT;

public class ListAvailableCoursesStudentController {
    private final AuthorizationService authz;
    private final StudentRepository studentRepo;
    private final ListCoursesService svc;

    public ListAvailableCoursesStudentController() {
        var repos = PersistenceContext.repositories();
        this.authz = AuthzRegistry.authorizationService();

        this.studentRepo = repos.students();

        this.svc = new ListCoursesService();
    }

    /**
     * @return DTO list with the courses
     */
    public List<AvailableCourseDTO> availableCourses() throws IllegalStateException {
        if(!this.authz.isAuthenticatedUserAuthorizedTo(STUDENT)) {
            throw new IllegalStateException("User is not of an appropriate role");
        }

        var student = this.studentRepo.findBySystemUser(getUser())
                .orElseThrow(() -> new IllegalStateException("User not registered as Student"));

        return new AvailableCourseDTOMapper().toDTO(this.svc.studentIsEnrolledOrCanEnroll(student));

    }

    private SystemUser getUser() throws IllegalStateException {
        return this.authz.session()
                .orElseThrow(() -> new IllegalStateException("User not logged in"))
                .authenticatedUser();
    }
}
