package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.clientusermanagement.application.AcceptRefuseSignupFactory;
import eapli.base.clientusermanagement.application.AcceptRefuseSignupRequestController;
import eapli.base.myclientuser.application.SignupController;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Action;

import eapli.base.infrastructure.bootstrapers.UsersBootstrapperBase;
import eapli.framework.infrastructure.authz.domain.model.Role;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeacherBootstrapper extends UsersBootstrapperBase  implements Action {


    private static final Logger LOGGER = LoggerFactory.getLogger(eapli.base.infrastructure.bootstrapers.demo.TeacherBootstrapper.class);

    private final SignupController signupController = new SignupController();
    private final AcceptRefuseSignupRequestController acceptController = AcceptRefuseSignupFactory.build();

    @Override
    public boolean execute(){
        // some users that signup and are approved
        registerTeacher( "Password1", "Jonas", "Antunes", "jonas@teacher.com",
                "isep435");
        registerTeacher( "Password1", "Marco", "Maia", "marco@teacher.com", "isep232");

        // some users that signup but the approval is pending. use the backoffice
        // application to approve these
        registerTeacher("Password1", "Peps", "Peps One", "Peps1@teacher.com", "isep121");
        registerTeacher("Password1", "Peps", "Peps Two", "Peps2@teacher.com", "isep122");
        registerTeacher("Password1", "Peps", "Peps Three", "Peps3@teacher.com", "isep123");
        registerTeacher("Password1", "Peps", "Peps Four", "Peps4@teacher.com", "isep124");

        return true;
    }

    public void registerTeacher(final String password, final String firstName,
                                           final String lastName, final String email, final String mecanographicNumber) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.TEACHER);

        registerUser(email, password, firstName, lastName, email, roles);
    }


}