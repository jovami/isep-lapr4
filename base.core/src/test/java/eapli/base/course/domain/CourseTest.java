package eapli.base.course.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @BeforeAll
    static void BeforeEach() {
    }

    @Test
    void testSetName() {
        String test = "TestName";
        Course course = new Course();
        course.setName(test);
        assertEquals(test, course.getName());
    }

    @Test
    void closeCourse() {
        Course course = new Course();
        course.close();
        assertEquals(CourseState.CLOSED, course.state());
    }

    @Test
    void openEnrollments() {
        Course course = new Course();
        course.openEnrollments();
        assertEquals(CourseState.ENROLL, course.state());
    }

    @Test
    void openCourse() {
        Course course = new Course();
        course.open();
        assertEquals(CourseState.OPEN, course.state());
    }

    @Test
    void closeEnrollments() {
        Course course = new Course();
        course.closeEnrollments();
        assertEquals(CourseState.INPROGRESS, course.state());
    }

    @Test
    void createdCourse() {
        Course course = new Course();
        course.createdCourse();
        assertEquals(CourseState.CLOSE, course.state());
    }

    @Test
    void setDescription() {
        String test = "TestDescription";
        Course course = new Course();
        course.setDescription(test);
        assertEquals(test, course.getDescription());
    }

    @Test
    void setCourseDescription() {
        String test = "TestDescription";
        CourseDescription course = new CourseDescription(test);
        assertEquals(test, course.toString());
    }

    @Test
    void setNegativeCapacities() {
        int testMin = -5;
        int testMax = -10;

        Course course = new Course();

        assertFalse(course.setCapacity(testMin, testMax));
        assertEquals(-1, course.getCapacity().getMinStudentsEnrolled());
        assertEquals(-1, course.getCapacity().getMaxStudentsEnrolled());
    }

    @Test
    void setInversedCapacity() {
        int testMin = 40;
        int testMax = 10;

        Course course = new Course();

        assertFalse(course.setCapacity(testMin, testMax));
        assertEquals(-1, course.getCapacity().getMinStudentsEnrolled());
        assertEquals(-1, course.getCapacity().getMaxStudentsEnrolled());
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

        Course course = new Course();

        course.setCapacity(testMin, testMax);
        assertEquals(testMin, course.getCapacity().getMinStudentsEnrolled());
        assertEquals(testMax, course.getCapacity().getMaxStudentsEnrolled());
    }

    @Test
    void setMinNegativeCapacity() {
        int testMin = -5;
        int testMax = 10;

        Course course = new Course();

        assertFalse(course.setCapacity(testMin, testMax));
        assertEquals(-1, course.getCapacity().getMinStudentsEnrolled());
        assertEquals(-1, course.getCapacity().getMaxStudentsEnrolled());
    }

    @Test
    void setMaxNegativeCapacity() {
        int testMin = 5;
        int testMax = -10;

        Course course = new Course();

        assertFalse(course.setCapacity(testMin, testMax));
        assertEquals(-1, course.getCapacity().getMinStudentsEnrolled());
        assertEquals(-1, course.getCapacity().getMaxStudentsEnrolled());
    }

    @Test
    void setDuration() throws ParseException {

        String startDateString = "1/1/2020";
        String endDateString = "1/1/2023";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date startDate = df.parse(startDateString);
        Date endDate = df.parse(endDateString);

        Course course = new Course();

        assertTrue(course.setDuration(startDate, endDate));
        assertEquals(startDate, course.getDuration().startDate());
        assertEquals(endDate, course.getDuration().endDate());
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

        String startDateString = "1/1/2020";
        String endDateString = "1/1/2023";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date startDate = df.parse(startDateString);
        Date endDate = df.parse(endDateString);

        CourseDuration duration = new CourseDuration(startDate, endDate);
        assertEquals(startDate, duration.getStartDate());
        assertEquals(endDate, duration.getEndDate());
        assertEquals("Start date: " + startDate.toString() + "\tEnd date: " + endDate.toString(), duration.toString());
    }

    @Test
    void setInvertedDatesDuration() throws ParseException {

        String startDateString = "1/1/2023";
        String endDateString = "1/1/2020";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date startDate = df.parse(startDateString);
        Date endDate = df.parse(endDateString);

        Course course = new Course();

        assertFalse(course.setDuration(startDate, endDate));
        assertNull(course.getDuration().endDate());
        assertNull(course.getDuration().startDate());
    }

    @Test
    void testSameCourse() {
        Course c1 = new Course();
        c1.setName("c1");
        assertTrue(c1.sameAs(c1));
    }

    @Test
    void testCourseSameName() {
        Course c1 = new Course();
        c1.setName("c1");
        Course c2 = new Course();
        c2.setName("c1");
        assertTrue(c1.sameAs(c2));
    }

    @Test
    void testSameAsNotCourse() {
        Course c1 = new Course();
        c1.setName("c1");
        CourseDuration c2 = new CourseDuration();
        assertFalse(c1.sameAs(c2));
    }

    @Test
    void testDifferentName() {
        Course c1 = new Course();
        c1.setName("c1");
        Course c2 = new Course();
        c2.setName("c2");
        assertFalse(c1.sameAs(c2));
    }

    @Test
    void withoutIdentity() {
        Course c1 = new Course();
        c1.setName("c1");
        assertFalse(c1.hasIdentity(1));
    }

    @Test
    void sameIdentity() {
        Course c1 = new Course();
        c1.setName("c1");
        assertEquals(0, c1.compareTo(0));
    }

    @Test
    void biggerIdentity() {
        Course c1 = new Course();
        c1.setName("c1");
        assertEquals(-1, c1.compareTo(10));
    }

    @Test
    void smallerIdentity() {
        Course c1 = new Course();
        c1.setName("c1");
        assertEquals(1, c1.compareTo(-10));
    }

    @Test
    void ensureCourseStateCannotGoBackToOpen() {
        CourseState state;
        var c1 = new Course();
        c1.open().onLeft(__ -> fail("Created courses should be able to be opened"));

        // sanity check
        assertEquals(CourseState.OPEN, c1.state(), "Course state should've been OPEN");
        c1.open().onRight(__ -> fail("Opening an open course should not alter its state"));
        assertEquals(CourseState.OPEN, c1.state());


        // check return values are correct
        // && also ensure the course state actually changed

        c1.openEnrollments();
        state = c1.state();
        c1.open().onRight(__ -> fail("Courses open to enrollments cannot be re-opened"));
        assertEquals(state, c1.state(), "Course cannot go back to OPEN after open to enrollments");

        c1.closeEnrollments();
        state = c1.state();
        c1.open().onRight(__ -> fail("Courses closed to enrollments cannot be re-opened"));
        assertEquals(state, c1.state(), "Course cannot go back to OPEN after enrollments are closed");

        c1.close();
        state = c1.state();
        c1.open().onRight(__ -> fail("Closed courses cannot be re-opened"));
        assertEquals(state, c1.state(), "Course cannot go back to OPEN after being closed");
    }

    // }
    @Test
    void ensureCreatedCourseCanBeClosed() {
        var c1 = new Course();

        c1.close().onLeft(__ -> fail("Created courses should be able to be closed"));
        assertEquals(CourseState.CLOSED, c1.state(), "Created course should be able to be closed");
    }

    @Test
    void ensureOpenCourseCanBeClosed() {
        var c1 = new Course();
        c1.open();

        var errMsg = "Opened courses should be able to be closed";

        c1.close().onLeft(__ -> fail(errMsg));
        assertEquals(CourseState.CLOSED, c1.state(), errMsg);
    }

    @Test
    void ensureOpenToEnrollmentsCourseCanBeClosed() {
        var c1 = new Course();
        c1.open();
        c1.openEnrollments();

        var errMsg = "Courses opened to enrollments should be able to be closed";

        c1.close().onLeft(__ -> fail(errMsg));
        assertEquals(CourseState.CLOSED, c1.state(), errMsg);
    }

    @Test
    void ensureClosedToEnrollmentsCourseCanBeClosed() {
        var c1 = new Course();
        c1.open();
        c1.openEnrollments();
        c1.closeEnrollments();

        var errMsg = "Courses closed to enrollments should be able to be closed";

        c1.close().onLeft(__ -> fail(errMsg));
        assertEquals(CourseState.CLOSED, c1.state(), errMsg);
    }

    @Test
    void ensureClosingClosedCourseDoesNotAlterState() {
        var c1 = new Course();
        c1.open();
        c1.openEnrollments();
        c1.closeEnrollments();
        c1.close();

        var errMsg = "Closing an already closed course should not alter its state";

        c1.close().onRight(__ -> fail(errMsg));
        assertEquals(CourseState.CLOSED, c1.state(), errMsg);
    }
}
