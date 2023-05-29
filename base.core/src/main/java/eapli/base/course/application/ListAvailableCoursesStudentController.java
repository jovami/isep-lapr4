package eapli.base.course.application;

import static eapli.base.clientusermanagement.usermanagement.domain.BaseRoles.STUDENT;

import java.util.List;

import eapli.base.clientusermanagement.application.MyUserService;
import eapli.base.course.dto.AvailableCourseDTO;
import eapli.base.course.dto.AvailableCourseDTOMapper;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListAvailableCoursesStudentController {
    private final AuthorizationService authz;
    private final ListCoursesService svc;

    public ListAvailableCoursesStudentController() {
        this.authz = AuthzRegistry.authorizationService();

        this.svc = new ListCoursesService();
    }

    /**
     * @return DTO list with the courses
     */
    public List<AvailableCourseDTO> availableCourses() throws IllegalStateException {
        this.authz.ensureAuthenticatedUserHasAnyOf(STUDENT);

        var student = new MyUserService().currentStudent();
        return new AvailableCourseDTOMapper().toDTO(this.svc.studentIsEnrolledOrCanEnroll(student));
    }
}
