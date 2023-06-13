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

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseID;
import eapli.base.course.repositories.CourseRepository;
import eapli.base.enrollment.domain.Enrollment;
import eapli.base.enrollment.repositories.EnrollmentRepository;
import eapli.base.infrastructure.bootstrapers.UsersBootstrapperBase;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 *
 * @author Paulo Sousa
 */
public class StudentBootstrapper extends UsersBootstrapperBase implements Action {
    // private static final Logger LOGGER = LoggerFactory.getLogger(StudentBootstrapper.class);

    // private final SignupController signupController = new SignupController();
    private final EnrollmentRepository enrollmentRepository = PersistenceContext.repositories().enrollments();
    private final CourseRepository courseRepository = PersistenceContext.repositories().courses();
    private final StudentRepository studentRepository = PersistenceContext.repositories().students();

    @Override
    public boolean execute() {
        registerStudent("johnny", "Password1", "Johnny", "Skrillex",
                "johnny@student.com", "isep192", "Johnny Skrillex", "Johnny",
                "1999-01-01", "123123125");

        registerStudent("mary", "Password1", "Mary", "Smith",
                "mary@student.com", "isep232", "Mary Smith", "Mary",
                "1999-01-01", "123123123");
        registerStudent("tiago", "Password1", "Mary", "Smith",
                "mary@student.com", "isep237", "Mary Smith", "Mary",
                "1999-01-01", "123123129");

        registerStudent("sairy", "Password2", "Sairy", "Miller",
                "sairy@student.com", "isep666", "Sairy Miller", "Sairy",
                "2000-02-29", "666333999");

        enrollStudents();


        ////////////////////////////////////////////
        //Test students for CSV bulk enroll students
        registerStudent("quim", "Password1", "quim", "Smith",
                "quim@student.com", "isep500", "quim Smith", "quim",
                "1999-01-01", "122222229");
        registerStudent("jose", "Password1", "jose", "Smith",
                "jose@student.com", "isep501", "jose Smith", "jose",
                "1999-01-01", "122222333");

        return true;
    }

    private void registerStudent(final String username, final String password, final String firstName,
            final String lastName,
            final String email, final String mecanographicNumber, final String fullName,
            final String shortName, final String dateOfBirth, final String taxPayerNumber) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.STUDENT);

        var user = registerUser(username, password, firstName, lastName, email, roles);
        registerStudent(user, mecanographicNumber, fullName, shortName, dateOfBirth, taxPayerNumber);

    }

    private void enrollStudents() {
        Course course = courseRepository.ofIdentity(CourseID.valueOf("Fisica-1")).orElseThrow();
        Student student = studentRepository.findByUsername(Username.valueOf("mary")).orElseThrow();
        enrollmentRepository.save(new Enrollment(course, student));
        Student student1 = studentRepository.findByUsername(Username.valueOf("tiago")).orElseThrow();
        enrollmentRepository.save(new Enrollment(course, student1));
    }

    /*
     * @Override
     * public boolean execute() {
     * // some users that signup and are approved
     * signupAndApprove("johnny","Password1", "Johnny", "Skrillex",
     * "johnny@student.com",
     * TestDataConstants.USER_TEST1);
     * signupAndApprove("mary","Password1", "Mary", "Smith", "mary@student.com",
     * "isep959");
     * //signupAndApprove("mary2","Password1", "Mary2", "Smith2",
     * "mary2@student.com",
     * // "isep958");
     * //signupAndApprove("mary3","Password1", "Mary3", "Smith3",
     * "mary3@student.com",
     * // "isep957");
     *
     *
     *
     * // some users that signup but the approval is pending. use the backoffice
     * // application to approve these
     * signup("mary23","Password1", "Marry", "Smith One", "mary1@student.com",
     * "isep111");
     * //courseApplication(courseApplicationController.findStudentByUsername(
     * Username.valueOf("mary")),
     * // courseApplicationController.findCourseByCourseName(CourseName.valueOf(
     * "Fisica")));
     * //enrollmentRequest(enrollmentRequestController.getCourses().iterator().next(
     * ));
     *//*
        * signup("Password1", "Mary", "Smith Two", "mary2@student.com", "isep222");
        * signup("Password1", "Mary", "Smith Three", "mary3@student.com", "isep333");
        * signup("Password1", "Mary", "Smith Four", "mary4@student.com", "isep444");
        *//*
           * return true;
           * }
           *
           * private SignupRequest signupAndApprove(final String username, final String
           * password, final String firstName,
           * final String lastName, final String email, final String mecanographicNumber)
           * {
           * SignupRequest request = null;
           * try {
           * request = signupController.signup(username, password, firstName, lastName,
           * email, mecanographicNumber);
           * acceptController.acceptSignupRequest(request);
           * } catch (final ConcurrencyException | IntegrityViolationException e) {
           * // ignoring exception. assuming it is just a primary key violation
           * // due to the tentative of inserting a duplicated user
           * LOGGER.warn("Assuming {} already exists (activate trace log for details)",
           * username);
           * LOGGER.trace("Assuming existing record", e);
           * }
           * return request;
           * }
           *
           * private SignupRequest signup(final String username, final String password,
           * final String firstName,
           * final String lastName, final String email, final String mecanographicNumber)
           * {
           * SignupRequest request = null;
           * try {
           * request = signupController.signup(username, password, firstName, lastName,
           * email, mecanographicNumber);
           * } catch (final ConcurrencyException | IntegrityViolationException e) {
           * // ignoring exception. assuming it is just a primary key violation
           * // due to the tentative of inserting a duplicated user
           * LOGGER.warn("Assuming {} already exists (activate trace log for details)",
           * username);
           * LOGGER.trace("Assuming existing record", e);
           * }
           * return request;
           * }
           */
    /*
     * private EnrollmentRequest enrollmentRequest(Course course) {
     * EnrollmentRequest request = null;
     * try {
     * request = enrollmentRequestController.createEnrollmentRequest(course);
     * } catch (final ConcurrencyException | IntegrityViolationException e) {
     * // ignoring exception. assuming it is just a primary key violation
     * // due to the tentative of inserting a duplicated user
     * LOGGER.warn("Assuming {} already exists (activate trace log for details)",
     * request.student().identity());
     * LOGGER.trace("Assuming existing record", e);
     * }
     * return request;
     * }
     */
}
