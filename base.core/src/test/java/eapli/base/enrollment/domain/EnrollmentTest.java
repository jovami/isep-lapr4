package eapli.base.enrollment.domain;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.StudentBuilder;
import eapli.base.course.domain.Course;
import eapli.framework.infrastructure.authz.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentTest {
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;

    @BeforeEach
    public void setUp() throws ParseException {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date sDate = df.parse("20/05/2020");
        Date eDate = df.parse("20/09/2020");
        course1 = new Course("PYTHON-1", "Python for beginners :)", sDate, eDate);
        course2 = new Course("JAVA-3", "Java advanced!", sDate, eDate);

        var user1 = userBuilder.with("alexandre", "Password1", "Alexandre", "Moreira", "alexmoreira@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        var user2 = userBuilder.with("miguel", "Password1", "Miguel", "Novais", "mnovais672@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();

        final var studentBuilder = new StudentBuilder();
        studentBuilder.withSystemUser(user1).withMecanographicNumber("isep567").withFullName("Alexandre Moreira").
                withShortName("Alex").withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        student1 = studentBuilder.build();
        studentBuilder.withSystemUser(user2).withMecanographicNumber("isep568").withFullName("Miguel Novais").
                withShortName("Miguel").withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        student2 = studentBuilder.build();
    }

    @Test
    public void createEnrollmentShouldWork() {
        Enrollment enrollment = new Enrollment(course1, student1);

        assertEquals(course1, enrollment.course());
        assertEquals(student1, enrollment.student());
    }

    @Test
    void shouldNotCreateEnrollmentWithNullCourseName() {
        assertThrows(IllegalArgumentException.class, () -> new Enrollment(null, student1));
    }

    @Test
    void shouldNotCreateEnrollmentWithNullUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Enrollment(course1, null));
    }

    @Test
    void sameAsWithSameEnrollmentShouldReturnTrue() {
        Enrollment enrollment = new Enrollment(course1, student1);
        assertTrue(enrollment.sameAs(enrollment));
    }

    @Test
    void sameAsWithDifferentCourseShouldReturnFalse() {
        Enrollment enrollment = new Enrollment(course1, student1);
        Enrollment enrollment2 = new Enrollment(course2, student1);
        assertFalse(enrollment.sameAs(enrollment2));
    }

    @Test
    void sameAsWithDifferentStudentShouldReturnFalse() {
        Enrollment enrollment = new Enrollment(course1, student1);
        Enrollment enrollment2 = new Enrollment(course1, student2);
        assertFalse(enrollment.sameAs(enrollment2));
    }

    @Test
    void sameAsWithNullShouldReturnFalse() {
        Enrollment enrollment = new Enrollment(course1, student1);
        assertFalse(enrollment.sameAs(null));
    }
}
