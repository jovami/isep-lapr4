package eapli.base.clientusermanagement.usermanagement.application;

import eapli.base.clientusermanagement.domain.users.Manager;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.ManagerRepository;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.Iterator;

public class ListUsersService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ManagerRepository managerRepository;

    public ListUsersService()
    {
        var repos = PersistenceContext.repositories();
        this.teacherRepository = repos.teachers();
        this.studentRepository = repos.students();
        this.managerRepository = repos.managers();
    }



    public Iterable<Teacher> listTeachers(){return this.teacherRepository.findAll();}
    public Iterable<Student> listStudents() {return this.studentRepository.findAll();}
    public Iterable<Manager> listManagers (){
        return this.managerRepository.findAll();
    }



    public Iterable<SystemUser> allUsersExceptPowerUser(Iterable<SystemUser> systemUsers) {

        /*for(SystemUser s: systemUsers)
        {
            if(!s.roleTypes().contains(BaseRoles.POWER_USER))
                System.out.println("Username: " + s.username().toString() +
                        " Name:" + s.name() + " Role: "+ s.roleTypes().toString());

        }*/

        Iterator<SystemUser> iterator = systemUsers.iterator();
        while (iterator.hasNext()) {
            SystemUser user = iterator.next();
            if (user.roleTypes().contains(BaseRoles.POWER_USER))
                iterator.remove();
        }
        return systemUsers;
    }

    public Iterable<SystemUser> userTeachers(Iterable<SystemUser> systemUsers)
    {
        Iterator<SystemUser> iterator = systemUsers.iterator();
        while (iterator.hasNext()) {
            SystemUser user = iterator.next();
            if (!user.roleTypes().contains(BaseRoles.TEACHER))
                iterator.remove();
        }
        return systemUsers;
    }

    public Iterable<SystemUser> userStudents(Iterable<SystemUser> systemUsers)
    {
        Iterator<SystemUser> iterator = systemUsers.iterator();
        while (iterator.hasNext()) {
            SystemUser user = iterator.next();
            if (!user.roleTypes().contains(BaseRoles.STUDENT))
                iterator.remove();
        }
        return systemUsers;
    }

    public Iterable<SystemUser> userManagers(Iterable<SystemUser> systemUsers)
    {
        Iterator<SystemUser> iterator = systemUsers.iterator();
        while (iterator.hasNext()) {
            SystemUser user = iterator.next();
            if (!user.roleTypes().contains(BaseRoles.MANAGER))
                iterator.remove();
        }
        return systemUsers;
    }
}
