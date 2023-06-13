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
package eapli.base.infrastructure.bootstrapers.demo;

import java.util.HashSet;
import java.util.Set;

import eapli.base.clientusermanagement.usermanagement.application.AddUserController;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.infrastructure.bootstrapers.UsersBootstrapperBase;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Role;

/**
 * @author Paulo Gandra Sousa
 */
public class ManagerBootstrapper extends UsersBootstrapperBase implements Action {

    private static final String PASSWORD = "Password1";
    private static final String EMAIL = "ruben@manager.com";
    private static final String EMAIL2 = "diogo@manager.com";

    AddUserController controller = new AddUserController();

    @Override
    public boolean execute() {
        registerManager("ruben", PASSWORD, "Ruben", "Ferreira", EMAIL,
                "Ruben tiago Ferreira", "Ruben Ferreira", "1998-01-01", "123456789");
        registerManager("diogo", PASSWORD, "Diogo", "Napoles", EMAIL2,
                "Diogo jose Napoles", "Diogo Napoles", "1997-01-01", "345456789");
        return true;
    }

    private void registerManager(final String username, final String password, final String firstName,
            final String lastName, final String email, final String fullName,
            final String shortName, final String dateOfBirth, final String taxPayerNumber) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.MANAGER);

        var user = registerUser(username, password, firstName, lastName, email, roles);
        registerManagerRepo(user, fullName, shortName, dateOfBirth, taxPayerNumber);
    }
}
