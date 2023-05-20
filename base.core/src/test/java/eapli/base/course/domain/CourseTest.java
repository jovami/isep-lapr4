package eapli.base.course.domain;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.TeacherBuilder;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;

class CourseTest {
    private Course course;

    @BeforeEach
    void setUp(){
        String startDateString = "1/1/2020";
        String endDateString = "1/1/2023";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date startDate = df.parse(startDateString);
            Date endDate = df.parse(endDateString);
            course = new Course(CourseName.valueOf("curso"), CourseDescription.valueOf("description"), startDate, endDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void closeCourse() {
        course.close();
        assertEquals(CourseState.CLOSED, course.state());
    }

    @Test
    void openEnrollments() {
        course.open();
        course.openEnrollments();
        assertEquals(CourseState.ENROLL, course.state());
    }

    @Test
    void openCourse() {
        course.open();
        assertEquals(CourseState.OPEN, course.state());
    }

    @Test
    void closeEnrollments() {
        course.open();
        course.openEnrollments();
        course.closeEnrollments();
        assertEquals(CourseState.INPROGRESS, course.state());
    }

    @Test
    void createdCourse() {
        course.createdCourse();
        assertEquals(CourseState.CLOSE, course.state());
    }


    @Test
    void setCourseDescription() {
        assertDoesNotThrow(() -> new CourseDescription("TestDescription"));
    }

    @Test
    void setNegativeCapacities() {
        int testMin = -5;
        int testMax = -10;

        assertFalse(course.setCapacity(testMin, testMax));
        assertEquals(-1, course.capacity().getMinStudentsEnrolled());
        assertEquals(-1, course.capacity().getMaxStudentsEnrolled());
    }

    @Test
    void setInversedCapacity() {
        int testMin = 40;
        int testMax = 10;

        assertFalse(course.setCapacity(testMin, testMax));
        assertEquals(-1, course.capacity().getMinStudentsEnrolled());
        assertEquals(-1, course.capacity().getMaxStudentsEnrolled());

    }

    @Test
    void setConstructorCapacity() {
        int testMin = 20;
        int testMax = 30;

        CourseCapacity capacity = new CourseCapacity(testMin, testMax);
        assertEquals(testMin, capacity.getMinStudentsEnrolled());
        assertEquals(testMax, capacity.getMaxStudentsEnrolled());
    }

    @Test
    void setRightCapacity() {
        int testMin = 5;
        int testMax = 20;

        course.setCapacity(testMin, testMax);
        assertEquals(testMin, course.capacity().getMinStudentsEnrolled());
        assertEquals(testMax, course.capacity().getMaxStudentsEnrolled());
    }

    @Test
    void setMinNegativeCapacity() {
        int testMin = -5;
        int testMax = 10;

        assertFalse(course.setCapacity(testMin, testMax));
        assertEquals(-1, course.capacity().getMinStudentsEnrolled());
        assertEquals(-1, course.capacity().getMaxStudentsEnrolled());
    }

    @Test
    void setMaxNegativeCapacity() {
        int testMin = 5;
        int testMax = -10;

        assertFalse(course.setCapacity(testMin, testMax));
        assertEquals(-1, course.capacity().getMinStudentsEnrolled());
        assertEquals(-1, course.capacity().getMaxStudentsEnrolled());
    }

    @Test
    void setTrueCourseDuration() throws ParseException {

        String startDateString = "1/1/2020";
        String endDateString = "1/1/2023";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date startDate = df.parse(startDateString);
        Date endDate = df.parse(endDateString);

        CourseDuration duration = new CourseDuration();
        Assertions.assertNull(duration.getEndDate());
        Assertions.assertNull(duration.getStartDate());
        assertTrue(duration.setIntervalDate(startDate, endDate));
        assertEquals(startDate, duration.getStartDate());
        assertEquals(endDate, duration.getEndDate());
    }

    @Test
    void setFalseCourseDuration() throws ParseException {

        String startDateString = "1/1/2023";
        String endDateString = "1/1/2020";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date startDate = df.parse(startDateString);
        Date endDate = df.parse(endDateString);

        CourseDuration duration = new CourseDuration();
        assertFalse(duration.setIntervalDate(startDate, endDate));
    }

    @Test
    void CourseDuration() throws ParseException {

        String startDateString = "1/1/2025";
        String endDateString = "1/1/2023";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date startDate = df.parse(startDateString);
        Date endDate = df.parse(endDateString);
        AtomicReference<CourseDuration> duration = new AtomicReference<>(new CourseDuration());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> duration.set(new CourseDuration(startDate, endDate)));
    }

    @Test
    void testSameCourse() {
        assertTrue(course.sameAs(course));
    }

    @Test
    void testCourseSameName() {
        String startDateString = "1/1/2020";
        String endDateString = "1/1/2023";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Course c2;
        try {
            Date startDate = df.parse(startDateString);
            Date endDate = df.parse(endDateString);
            c2 = new Course(CourseName.valueOf("curso"), CourseDescription.valueOf("descrição"), startDate, endDate);
            assertTrue(course.sameAs(c2));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSameAsNotCourse() {
        CourseDuration c2 = new CourseDuration();
        assertFalse(course.sameAs(c2));
    }

    @Test
    void testDifferentName() {
        String startDateString = "1/1/2020";
        String endDateString = "1/1/2023";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Course c2;
        try {
            Date startDate = df.parse(startDateString);
            Date endDate = df.parse(endDateString);
            c2 = new Course(CourseName.valueOf("Different"), CourseDescription.valueOf("descrição"), startDate, endDate);
            assertFalse(course.sameAs(c2));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        assertFalse(course.sameAs(c2));
    }

    @Test
    void withoutIdentity() {
        assertFalse(course.hasIdentity(1));
    }

    @Test
    void sameIdentity() {

        assertEquals(0, course.compareTo(0));
    }

    @Test
    void biggerIdentity() {

        assertEquals(-1, course.compareTo(10));
    }

    @Test
    void smallerIdentity() {

        assertEquals(1, course.compareTo(-10));

    }

    @Test
    void ensureCourseStateCannotGoBackToOpen() {
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
    void ensureCreatedCourseCanBeClosed() {

        course.close().onLeft(__ -> fail("Created courses should be able to be closed"));
        assertEquals(CourseState.CLOSED, course.state(), "Created course should be able to be closed");
    }

    @Test
    void ensureOpenCourseCanBeClosed() {
        course.open();

        var errMsg = "Opened courses should be able to be closed";

        course.close().onLeft(__ -> fail(errMsg));
        assertEquals(CourseState.CLOSED, course.state(), errMsg);
    }

    @Test
    void ensureOpenToEnrollmentsCourseCanBeClosed() {
        course.open();
        course.openEnrollments();

        var errMsg = "Courses opened to enrollments should be able to be closed";

        course.close().onLeft(__ -> fail(errMsg));
        assertEquals(CourseState.CLOSED, course.state(), errMsg);
    }

    @Test
    void ensureClosedToEnrollmentsCourseCanBeClosed() {
        course.open();
        course.openEnrollments();
        course.closeEnrollments();

        var errMsg = "Courses closed to enrollments should be able to be closed";

        course.close().onLeft(__ -> fail(errMsg));
        assertEquals(CourseState.CLOSED, course.state(), errMsg);
    }

    @Test
    void ensureClosingClosedCourseDoesNotAlterState() {
        course.open();
        course.openEnrollments();
        course.closeEnrollments();
        course.close();

        var errMsg = "Closing an already closed course should not alter its state";

        course.close().onRight(__ -> fail(errMsg));
        assertEquals(CourseState.CLOSED, course.state(), errMsg);
    }

    @Test
    void CapacityToString() {
        assertEquals("Min students enrolled: " + course.capacity().getMinStudentsEnrolled()
                + "\nMax students enrolled: " + course.capacity().getMaxStudentsEnrolled(),
                course.capacity().toString());
    }

    @Test
    void courseDescriptionNull() {
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
    void headTeacher() {
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
