package eapli.base.clientusermanagement.usermanagement.application;

import eapli.base.clientusermanagement.domain.users.Manager;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.repositories.ManagerRepository;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.repositories.TeacherRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

public class ListUsersService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ManagerRepository managerRepository;

    public ListUsersService() {
        var repos = PersistenceContext.repositories();
        this.teacherRepository = repos.teachers();
        this.studentRepository = repos.students();
        this.managerRepository = repos.managers();
    }

    public Iterable<Teacher> listTeachers() {
        return this.teacherRepository.findAll();
    }

    public Iterable<Student> listStudents() {
        return this.studentRepository.findAll();
    }

    public Iterable<Manager> listManagers() {
        return this.managerRepository.findAll();
    }

}
