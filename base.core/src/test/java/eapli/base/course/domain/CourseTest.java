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
        course = new Course(CourseName.valueOf("curso"), CourseDescription.valueOf("description"), CourseDuration.valueOf(startDate, endDate));
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
    public void setNegativeCapacities() {
        int testMin = -5;
        int testMax = -10;

        assertFalse(course.setCapacity(testMin, testMax));
        assertEquals(-1, course.capacity().minStudentsEnrolled());
        assertEquals(-1, course.capacity().maxStudentsEnrolled());
    }

    @Test
    public void setInversedCapacity() {
        int testMin = 40;
        int testMax = 10;

        assertFalse(course.setCapacity(testMin, testMax));
        assertEquals(-1, course.capacity().minStudentsEnrolled());
        assertEquals(-1, course.capacity().maxStudentsEnrolled());

    }

    @Test
    public void setConstructorCapacity() {
        int testMin = 20;
        int testMax = 30;

        CourseCapacity capacity = new CourseCapacity(testMin, testMax);
        assertEquals(testMin, capacity.minStudentsEnrolled());
        assertEquals(testMax, capacity.maxStudentsEnrolled());
    }

    @Test
    public void setRightCapacity() {
        int testMin = 5;
        int testMax = 20;

        course.setCapacity(testMin, testMax);
        assertEquals(testMin, course.capacity().minStudentsEnrolled());
        assertEquals(testMax, course.capacity().maxStudentsEnrolled());
    }

    @Test
    public void setMinNegativeCapacity() {
        int testMin = -5;
        int testMax = 10;

        assertFalse(course.setCapacity(testMin, testMax));
        assertEquals(-1, course.capacity().minStudentsEnrolled());
        assertEquals(-1, course.capacity().maxStudentsEnrolled());
    }

    @Test
    public void setMaxNegativeCapacity() {
        int testMin = 5;
        int testMax = -10;

        assertFalse(course.setCapacity(testMin, testMax));
        assertEquals(-1, course.capacity().minStudentsEnrolled());
        assertEquals(-1, course.capacity().maxStudentsEnrolled());
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
        c2 = new Course(CourseName.valueOf("curso"), CourseDescription.valueOf("descrição"), CourseDuration.valueOf(startDate, endDate));
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
        c2 = new Course(CourseName.valueOf("Different"), CourseDescription.valueOf("descrição"), CourseDuration.valueOf(startDate, endDate));
        assertFalse(course.sameAs(c2));
        assertFalse(course.sameAs(c2));
    }

    @Test
    public void withoutIdentity() {
        assertFalse(course.hasIdentity(1));
    }

    @Test
    public void sameIdentity() {

        assertEquals(0, course.compareTo(0));
    }

    @Test
    public void biggerIdentity() {

        assertEquals(-1, course.compareTo(10));
    }

    @Test
    public void smallerIdentity() {

        assertEquals(1, course.compareTo(-10));

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
    public void CapacityToString() {
        assertEquals("Min students enrolled: " + course.capacity().minStudentsEnrolled()
                        + "\nMax students enrolled: " + course.capacity().maxStudentsEnrolled(),
                course.capacity().toString());
    }

    @Test
    public void courseDescriptionNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CourseDescription(null);
        });
    }

    /*
     * @Test
     * void courseName() {
     * CourseName name = new CourseName("curso");
     * assertEquals(name,course.getCourseName());
     * }
     */
    @Test
    public void headTeacher() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        userBuilder.with("teacher", "Password1", "first", "last", "email@teacher.com").withRoles(BaseRoles.TEACHER);
        TeacherBuilder teacherBuilder = new TeacherBuilder().withSystemUser(userBuilder.build()).withAcronym("TCH")
                .withDateOfBirth("2003-10-10").withFullName("full").withTaxPayerNumber("123123123")
                .withShortName("short");
        Teacher teacher = teacherBuilder.build();
        course.setHeadTeacher(teacher);
        assertEquals(teacher, course.headTeacher());
    }
}
