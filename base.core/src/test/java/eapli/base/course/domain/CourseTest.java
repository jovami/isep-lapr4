package eapli.base.course.domain;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.TeacherBuilder;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    private Course course;

    @Before
    public void setUp() {
        String startDateString = "01/01/2020";
        String endDateString = "01/01/2023";

        var df = DateTimeFormatter.ofPattern("d/M/yyyy");

        var startDate = LocalDate.parse(startDateString, df);
        var endDate = LocalDate.parse(endDateString, df);
        var duration = CourseDuration.valueOf(startDate, endDate);

        var capacity = CourseCapacity.valueOf(1, 20);

        course = new Course(CourseID.valueOf("JAVA-1"), CourseDescription.valueOf("description"), duration, capacity);
    }

    @Test
    public void closeCourse() {
        course.close();
        assertEquals(CourseState.CLOSED, course.state());
    }

    @Test
    public void openEnrollments() {
        course.open();
        course.openEnrollments();
        assertEquals(CourseState.ENROLL, course.state());
    }

    @Test
    public void openCourse() {
        course.open();
        assertEquals(CourseState.OPEN, course.state());
    }

    @Test
    public void closeEnrollments() {
        course.open();
        course.openEnrollments();
        course.closeEnrollments();
        assertEquals(CourseState.INPROGRESS, course.state());
    }

    @Test
    public void createdCourse() {
        assertEquals(CourseState.CLOSE, course.state());
    }


    @Test
    public void setCourseDescription() {
        assertDoesNotThrow(() -> new CourseDescription("TestDescription"));
    }

    @Test
    public void ensureStartDateIsBeforeEndDate() throws ParseException {

        String startDateString = "01/01/2025";
        String endDateString = "01/01/2023";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy");

        LocalDate startDate = LocalDate.parse(startDateString, df);
        LocalDate endDate = LocalDate.parse(endDateString, df);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> CourseDuration.valueOf(startDate, endDate));
    }

    @Test
    public void testSameCourse() {
        assertTrue(course.sameAs(course));
    }

    @Test
    public void testCourseSameName() {
        Course c2;
        String startDateString = "01/01/2020";
        String endDateString = "01/01/2023";

        var df = DateTimeFormatter.ofPattern("d/M/yyyy");
        var startDate = LocalDate.parse(startDateString, df);
        var endDate = LocalDate.parse(endDateString, df);
        var capacity = CourseCapacity.valueOf(1, 20);
        c2 = new Course(CourseID.valueOf("JAVA-1"), CourseDescription.valueOf("descrição"), CourseDuration.valueOf(startDate, endDate), capacity);
        assertTrue(course.sameAs(c2));
    }

    @Test
    public void testSameAsNotCourse() {
        CourseDuration c2 = new CourseDuration();
        assertFalse(course.sameAs(c2));
    }

    @Test
    public void testDifferentName() {
        Course c2;
        String startDateString = "01/01/2020";
        String endDateString = "01/01/2023";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy");

        var startDate = LocalDate.parse(startDateString, df);
        var endDate = LocalDate.parse(endDateString, df);
        var capacity = CourseCapacity.valueOf(1, 20);
        c2 = new Course(CourseID.valueOf("Different-24"), CourseDescription.valueOf("descrição"), CourseDuration.valueOf(startDate, endDate), capacity);
        assertFalse(course.sameAs(c2));
        assertFalse(course.sameAs(c2));
    }

    @Test
    public void withoutIdentity() {
        assertFalse(course.hasIdentity(CourseID.valueOf("EAPLI-1")));
    }

    @Test
    public void sameIdentity() {

        assertEquals(0, course.compareTo(CourseID.valueOf("JAVA-1")));
    }

    @Test
    public void ensureCourseStateCannotGoBackToOpen() {
        CourseState state;
        course.open().onLeft(__ -> fail("Created courses should be able to be opened"));

        // sanity check
        assertEquals(CourseState.OPEN, course.state(), "Course state should've been OPEN");
        course.open().onRight(__ -> fail("Opening an open course should not alter its state"));
        assertEquals(CourseState.OPEN, course.state());

        // check return values are correct
        // && also ensure the course state actually changed

        course.openEnrollments();
        state = course.state();
        course.open().onRight(__ -> fail("Courses open to enrollments cannot be re-opened"));
        assertEquals(state, course.state(), "Course cannot go back to OPEN after open to enrollments");

        course.closeEnrollments();
        state = course.state();
        course.open().onRight(__ -> fail("Courses closed to enrollments cannot be re-opened"));
        assertEquals(state, course.state(), "Course cannot go back to OPEN after enrollments are closed");

        course.close();
        state = course.state();
        course.open().onRight(__ -> fail("Closed courses cannot be re-opened"));
        assertEquals(state, course.state(), "Course cannot go back to OPEN after being closed");
    }

    // }
    @Test
    public void ensureCreatedCourseCanBeClosed() {

        course.close().onLeft(__ -> fail("Created courses should be able to be closed"));
        assertEquals(CourseState.CLOSED, course.state(), "Created course should be able to be closed");
    }

    @Test
    public void ensureOpenCourseCanBeClosed() {
        course.open();

        var errMsg = "Opened courses should be able to be closed";

        course.close().onLeft(__ -> fail(errMsg));
        assertEquals(CourseState.CLOSED, course.state(), errMsg);
    }

    @Test
    public void ensureOpenToEnrollmentsCourseCanBeClosed() {
        course.open();
        course.openEnrollments();

        var errMsg = "Courses opened to enrollments should be able to be closed";

        course.close().onLeft(__ -> fail(errMsg));
        assertEquals(CourseState.CLOSED, course.state(), errMsg);
    }

    @Test
    public void ensureClosedToEnrollmentsCourseCanBeClosed() {
        course.open();
        course.openEnrollments();
        course.closeEnrollments();

        var errMsg = "Courses closed to enrollments should be able to be closed";

        course.close().onLeft(__ -> fail(errMsg));
        assertEquals(CourseState.CLOSED, course.state(), errMsg);
    }

    @Test
    public void ensureClosingClosedCourseDoesNotAlterState() {
        course.open();
        course.openEnrollments();
        course.closeEnrollments();
        course.close();

        var errMsg = "Closing an already closed course should not alter its state";

        course.close().onRight(__ -> fail(errMsg));
        assertEquals(CourseState.CLOSED, course.state(), errMsg);
    }

    @Test
    public void courseDescriptionNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CourseDescription(null);
        });
    }

    @Test
    public void headTeacher() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        userBuilder.with("teacher", "Password1", "first", "last", "email@teacher.com").withRoles(BaseRoles.TEACHER);
        TeacherBuilder teacherBuilder = new TeacherBuilder().withSystemUser(userBuilder.build()).withAcronym("TCH")
                .withDateOfBirth("2003-10-10").withFullName("full").withTaxPayerNumber("123123123")
                .withShortName("short");
        Teacher teacher = teacherBuilder.build();

        course.setHeadTeacher(teacher);
        assertTrue(course.headTeacher().isPresent());
        assertEquals(teacher, course.headTeacher().get());
    }
}
