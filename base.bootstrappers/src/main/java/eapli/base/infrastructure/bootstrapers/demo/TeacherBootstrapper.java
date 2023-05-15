package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Action;

import eapli.base.infrastructure.bootstrapers.UsersBootstrapperBase;
import eapli.framework.infrastructure.authz.domain.model.Role;

import java.util.HashSet;
import java.util.Set;

public class TeacherBootstrapper extends UsersBootstrapperBase implements Action {


    @SuppressWarnings("squid:S2068")
    /*private static final String PASSWORD = "Password1";
    private static final String EMAIL = "jonas@teacher.com";
    private static final String EMAIL2 = "marco@teacher.com";*/


    @Override
    public boolean execute(){
        registerTeacher( "jonas1","Password1", "Jonas", "Antunes", "jonas@teacher.com",
                "isep435");
        registerTeacher( "marco1","Password1", "Marco", "Maia", "marco@teacher.com", "isep232");


        registerTeacher("peps","Password1", "Peps", "Peps One", "Peps1@teacher.com", "isep121");
        registerTeacher("peps2","Password1", "Peps", "Peps Two", "Peps2@teacher.com", "isep122");
        registerTeacher("peps3","Password1", "Peps", "Peps Three", "Peps3@teacher.com", "isep123");
        registerTeacher("peps4","Password1", "Peps", "Peps Four", "Peps4@teacher.com", "isep124");

        return true;
    }

    public void registerTeacher(final String username, final String password, final String firstName,
                                           final String lastName, final String email, final String mecanographicNumber) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.TEACHER);

        registerUser(username, password, firstName, lastName, email, roles);
    }


}
