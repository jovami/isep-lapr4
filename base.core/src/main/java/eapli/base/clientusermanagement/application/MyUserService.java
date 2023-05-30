package eapli.base.clientusermanagement.application;

import java.util.Optional;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

/**
 * MyUserService
 */
public final class MyUserService {

    private final AuthorizationService authz;

    private final StudentRepository studentRepo;
    private final TeacherRepository teacherRepo;

    // TODO: cache teachers/users as well?
    private Optional<SystemUser> current;

    public MyUserService() {
        var repos = PersistenceContext.repositories();

        this.authz = AuthzRegistry.authorizationService();

        this.studentRepo = repos.students();
        this.teacherRepo = repos.teachers();

        this.current = Optional.empty();
    }

    private final IllegalStateException error() {
        return new IllegalStateException("User not logged in");
    }

    public SystemUser currentUser() {
        // Use cached user if available
        if (this.current.isPresent())
            return this.current.get();

        var user = authz.session()
                .orElseThrow(this::error)
                .authenticatedUser();
        this.current = Optional.of(user);
        return user;
    }

    public Student currentStudent() {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.STUDENT);
        return this.studentRepo.findBySystemUser(currentUser())
            .orElseThrow(this::error);
    }

    public Teacher currentTeacher() {
        this.authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.TEACHER);
        return this.teacherRepo.findByUser(currentUser())
            .orElseThrow(this::error);
    }
}
