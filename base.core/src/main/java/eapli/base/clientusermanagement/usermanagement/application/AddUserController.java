/*
 * Copyright (c) 2013-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.base.clientusermanagement.usermanagement.application;

import java.util.Calendar;
import java.util.Set;

import eapli.base.clientusermanagement.domain.users.Manager;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.ManagerBuilder;
import eapli.base.clientusermanagement.usermanagement.domain.StudentBuilder;
import eapli.base.clientusermanagement.usermanagement.domain.TeacherBuilder;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.time.util.CurrentTimeCalendars;

/**
 * Created by nuno on 21/03/16.
 */
@UseCaseController
public class AddUserController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final UserManagementService userSvc = AuthzRegistry.userService();

    private SystemUser user;

    /**
     * Get existing RoleTypes available to the user.
     *
     * @return a list of RoleTypes
     */
    public Role[] getRoleTypes() {
        return BaseRoles.nonUserValues();
    }

    public SystemUser addUser(final String username, final String password, final String firstName,
            final String lastName,
            final String email, final Set<Role> roles, final Calendar createdOn) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MANAGER);

        this.user = userSvc.registerNewUser(username, password, firstName, lastName, email, roles, createdOn);

        return user;
    }

    public SystemUser addUser(final String username, final String password, final String firstName,
            final String lastName, final String email, final Set<Role> roles) {
        return addUser(username, password, firstName, lastName, email, roles, CurrentTimeCalendars.now());
    }

    public Teacher addTeacher(final SystemUser user, final String acronym, final String fullName,
            final String shortName,
            final String dateOfBirth, final String taxPayerNumber) {

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MANAGER);

        final var teacherBuilder = new TeacherBuilder();
        teacherBuilder.withSystemUser(user).withAcronym(acronym).withFullName(fullName).withShortName(shortName)
                .withDateOfBirth(dateOfBirth).withTaxPayerNumber(taxPayerNumber);

        return PersistenceContext.repositories().teachers().save(teacherBuilder.build());
    }

    public Student addStudent(final SystemUser user, final String mecanographicNumber, final String fullName,
            final String shortName,
            final String dateOfBirth, final String taxPayerNumber) {

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MANAGER);

        final var studentBuilder = new StudentBuilder();
        studentBuilder.withSystemUser(user).withMecanographicNumber(mecanographicNumber).withFullName(fullName)
                .withShortName(shortName).withDateOfBirth(dateOfBirth).withTaxPayerNumber(taxPayerNumber);

        return PersistenceContext.repositories().students().save(studentBuilder.build());
    }

    public Manager addManager(final SystemUser user, final String fullName, final String shortName,
            final String dateOfBirth,
            final String taxPayerNumber) {

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MANAGER);

        final var managerBuilder = new ManagerBuilder();
        managerBuilder.withSystemUser(user).withFullName(fullName).withShortName(shortName)
                .withDateOfBirth(dateOfBirth).withTaxPayerNumber(taxPayerNumber);

        return PersistenceContext.repositories().managers().save(managerBuilder.build());
    }

    public Iterable<Manager> listManagers() {
        return PersistenceContext.repositories().managers().findAll();
    }

}
