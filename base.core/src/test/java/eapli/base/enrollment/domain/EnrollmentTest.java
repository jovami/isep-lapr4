package eapli.base.enrollment.domain;


import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.StudentBuilder;
import eapli.base.course.domain.Course;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnrollmentTest {


    private Course course;
    private Student student;
    private String startDate="20/05/2020";
    private String endDate="20/09/2020";
    private SystemUser user;
    private final String username = "Tony";
    private final String mecanographicNumber = "isep567";
    private final String fullName = "Tony Stark";
    private final String shortName = "Tony";
    private final String dateOfBirth = "2001-01-01";
    private final String taxPayerNumber = "123756789";

    @BeforeEach
    public void setUp() throws ParseException {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date sDate = df.parse(startDate);
        Date eDate = df.parse(endDate);
        course = new Course("Fisics", "Fisics dos materiais", sDate, eDate);
        user = userBuilder.with(username, "Password1", "dummy", "dummy", "a@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        final var studentBuilder = new StudentBuilder();
        studentBuilder.withSystemUser(user).withMecanographicNumber(mecanographicNumber).withFullName(fullName).
                withShortName(shortName).withDateOfBirth(dateOfBirth).withTaxPayerNumber(taxPayerNumber);
        student = studentBuilder.build();
    }



   /* @Test
    public void testCreateEnrollment() {
        Enrollment enrollment = new Enrollment(courseName, username);

        assertEquals(courseName.getName(), enrollment.obtainCourseName());
        assertEquals(username.toString(), enrollment.obtainUsername());
    }

    @Test
    void testShouldNotCreateEnrollmentWithNullCourseName() {
        assertThrows(IllegalArgumentException.class, () -> new Enrollment(null, username));
    }

    @Test
    void shouldNotCreateEnrollmentWithNullUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Enrollment(courseName, null));
    }

    @Test
    public void testChangeCourseName() {
        Enrollment enrollment = new Enrollment(courseName, username);

        CourseName newCourseName = new CourseName("Web Development");
        enrollment.changeCourseName(newCourseName);

        assertEquals(newCourseName.getName(), enrollment.obtainCourseName());
    }

    @Test
    void shouldNotChangeCourseNameToNull() {
        Enrollment enrollment = new Enrollment(courseName, username);
        assertThrows(IllegalArgumentException.class, () -> enrollment.changeCourseName(null));
    }

    @Test
    public void testChangeUsername() {
        Enrollment enrollment = new Enrollment(courseName, username);

        var newUsername = Username.valueOf("testUser2");
        enrollment.changeUsername(newUsername);

        assertEquals(newUsername.toString(), enrollment.obtainUsername());
    }

    @Test
    void shouldNotChangeUsernameToNull() {
        Enrollment enrollment = new Enrollment(courseName, username);
        assertThrows(IllegalArgumentException.class, () -> enrollment.changeUsername(null));
    }

    @Test
    void testSameAsWithDifferentEnrollment() {
        Enrollment enrollment = new Enrollment(courseName, username);
        Enrollment enrollment2 = new Enrollment(new CourseName("JAVA-3"), username);
        assertFalse(enrollment.sameAs(enrollment2));
    }

    @Test
    void testSameAsWithNull() {
        Enrollment enrollment = new Enrollment(courseName, username);
        assertFalse(enrollment.sameAs(null));
    }

    @Test
    void testSameAsWithSameEnrollment() {
        Enrollment enrollment = new Enrollment(courseName, username);
        assertTrue(enrollment.sameAs(enrollment));
    }

    @Test
    void testSameAsWithDifferentCourseName() {
        Enrollment enrollment = new Enrollment(courseName, username);
        Enrollment enrollment2 = new Enrollment(new CourseName("JAVA-3"), username);
        assertFalse(enrollment.sameAs(enrollment2));
    }

    @Test
    void testSameAsWithDifferentUsername() {
        Enrollment enrollment = new Enrollment(courseName, username);
        Enrollment enrollment2 = new Enrollment(courseName, Username.valueOf("testUser2"));
        assertFalse(enrollment.sameAs(enrollment2));
    }*/

}
