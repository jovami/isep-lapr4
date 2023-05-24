package eapli.base.enrollment.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.StudentBuilder;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseFactory;
import eapli.base.course.dto.CreateCourseDTO;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;

public class EnrollmentTest {
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;

    @Before
    public void setUp() throws ParseException {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy");

        var sDate = LocalDate.parse("20/05/2020", df);
        var eDate = LocalDate.parse("20/09/2020", df);

        var dto1 = new CreateCourseDTO("PYTHON", 1L, "Python for beginners :)", sDate, eDate, 10, 24);
        var dto2 = new CreateCourseDTO("JAVA", 3L, "Java advanced!", sDate, eDate, 20, 34);

        course1 = new CourseFactory().build(dto1);
        course2 = new CourseFactory().build(dto2);

        var user1 = userBuilder.with("alexandre", "Password1", "Alexandre", "Moreira", "alexmoreira@gmail.com")
                .withRoles(BaseRoles.STUDENT).build();
        var user2 = userBuilder.with("miguel", "Password1", "Miguel", "Novais", "mnovais672@gmail.com")
                .withRoles(BaseRoles.STUDENT).build();

        final var studentBuilder = new StudentBuilder();
        studentBuilder.withSystemUser(user1).withMecanographicNumber("isep567").withFullName("Alexandre Moreira")
                .withShortName("Alex").withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        student1 = studentBuilder.build();
        studentBuilder.withSystemUser(user2).withMecanographicNumber("isep568").withFullName("Miguel Novais")
                .withShortName("Miguel").withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        student2 = studentBuilder.build();
    }

    @Test
    public void createEnrollmentShouldWork() {
        Enrollment enrollment = new Enrollment(course1, student1);

        assertEquals(course1, enrollment.course());
        assertEquals(student1, enrollment.student());
    }

    @Test
    public void shouldNotCreateEnrollmentWithNullCourseName() {
        assertThrows(IllegalArgumentException.class, () -> new Enrollment(null, student1));
    }

    @Test
    public void shouldNotCreateEnrollmentWithNullUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Enrollment(course1, null));
    }

    @Test
    public void sameAsWithSameEnrollmentShouldReturnTrue() {
        Enrollment enrollment = new Enrollment(course1, student1);
        assertTrue(enrollment.sameAs(enrollment));
    }

    @Test
    public void sameAsWithDifferentCourseShouldReturnFalse() {
        Enrollment enrollment = new Enrollment(course1, student1);
        Enrollment enrollment2 = new Enrollment(course2, student1);
        assertFalse(enrollment.sameAs(enrollment2));
    }

    @Test
    public void sameAsWithDifferentStudentShouldReturnFalse() {
        Enrollment enrollment = new Enrollment(course1, student1);
        Enrollment enrollment2 = new Enrollment(course1, student2);
        assertFalse(enrollment.sameAs(enrollment2));
    }

    @Test
    public void sameAsWithNullShouldReturnFalse() {
        Enrollment enrollment = new Enrollment(course1, student1);
        assertFalse(enrollment.sameAs(null));
    }
}
