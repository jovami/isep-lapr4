package eapli.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @BeforeAll
    static void BeforeEach(){
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
        course.closeCourse();
        assertEquals(CourseState.CLOSED,course.getState());
    }

    @Test
    void openEnrollments() {
            Course course = new Course();
            course.openEnrollments();
            assertEquals(CourseState.ENROLL,course.getState());
    }

    @Test
    void openCourse() {
            Course course = new Course();
            course.openCourse();
            assertEquals(CourseState.OPEN,course.getState());
    }

    @Test
    void closeEnrollments() {
            Course course = new Course();
            course.closeEnrollments();
            assertEquals(CourseState.INPROGRESS,course.getState());
    }

    @Test
    void createdCourse() {
            Course course = new Course();
            course.createdCourse();
            assertEquals(CourseState.CLOSE,course.getState());
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
        int testMin=-5;
        int testMax=-10;

        Course course = new Course();

        Assertions.assertFalse(course.setCapacity(testMin,testMax));
        assertEquals(-1,course.getCapacity().getMinStudentsEnrolled());
        assertEquals(-1,course.getCapacity().getMaxStudentsEnrolled());
    }

    @Test
    void setInversedCapacity() {
        int testMin=40;
        int testMax=10;

        Course course = new Course();

        Assertions.assertFalse(course.setCapacity(testMin,testMax));
        assertEquals(-1,course.getCapacity().getMinStudentsEnrolled());
        assertEquals(-1,course.getCapacity().getMaxStudentsEnrolled());
    }
    @Test
    void setConstructorCapacity() {
        int testMin=20;
        int testMax=30;

        CourseCapacity capacity= new CourseCapacity(testMin,testMax);
        assertEquals(testMin,capacity.getMinStudentsEnrolled());
        assertEquals(testMax,capacity.getMaxStudentsEnrolled());
    }

    @Test
    void setRightCapacity() {
        int testMin=5;
        int testMax=20;

        Course course = new Course();

        course.setCapacity(testMin,testMax);
        assertEquals(testMin, course.getCapacity().getMinStudentsEnrolled());
        assertEquals(testMax, course.getCapacity().getMaxStudentsEnrolled());
    }


    @Test
    void setMinNegativeCapacity() {
        int testMin=-5;
        int testMax=10;

        Course course = new Course();

        Assertions.assertFalse(course.setCapacity(testMin,testMax));
        assertEquals(-1, course.getCapacity().getMinStudentsEnrolled());
        assertEquals(-1, course.getCapacity().getMaxStudentsEnrolled());
    }
    @Test
    void setMaxNegativeCapacity() {
        int testMin=5;
        int testMax=-10;

        Course course = new Course();


        Assertions.assertFalse(course.setCapacity(testMin,testMax));
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

        Assertions.assertTrue(course.setDuration(startDate,endDate));
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
        Assertions.assertTrue(duration.setIntervalDate(startDate,endDate));
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
        Assertions.assertFalse(duration.setIntervalDate(startDate,endDate));
    }
    @Test
    void CourseDuration() throws ParseException {

        String startDateString = "1/1/2020";
        String endDateString = "1/1/2023";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date startDate = df.parse(startDateString);
        Date endDate = df.parse(endDateString);

        CourseDuration duration = new CourseDuration(startDate,endDate);
        assertEquals(startDate, duration.getStartDate());
        assertEquals(endDate, duration.getEndDate());
        assertEquals( "Start date: "+startDate.toString()+"\tEnd date: "+endDate.toString(),duration.toString());
    }
    @Test
    void setInvertedDatesDuration() throws ParseException {

        String startDateString = "1/1/2023";
        String endDateString = "1/1/2020";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date startDate = df.parse(startDateString);
        Date endDate = df.parse(endDateString);

        Course course = new Course();

        Assertions.assertFalse(course.setDuration(startDate,endDate));
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
        Assertions.assertFalse(c1.hasIdentity(1));
    }

    @Test
    void sameIdentity() {
        Course c1 = new Course();
        c1.setName("c1");
        Assertions.assertEquals(0,c1.compareTo(0));
    }

    @Test
    void biggerIdentity() {
        Course c1 = new Course();
        c1.setName("c1");
        Assertions.assertEquals(-1,c1.compareTo(10));
    }

    @Test
    void smallerIdentity() {
        Course c1 = new Course();
        c1.setName("c1");
        Assertions.assertEquals(1,c1.compareTo(-10));
    }

}