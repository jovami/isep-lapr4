package eapli.base.exam.domain.regular_exam;

import eapli.base.course.domain.Course;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamDate;
import eapli.base.exam.domain.regular_exam.valueobjects.RegularExamSpecification;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RegularExamTest {

    private RegularExamDate regularExamDate;
    private Course course;

    @Before
    public void BeforeEach(){
        String openDateString = "2023-10-10 16:00";
        String closeDateString = "2023-10-10 18:00";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date openDate = df.parse(openDateString);
            Date closeDate = df.parse(closeDateString);
            regularExamDate =  RegularExamDate.valueOf(openDate,closeDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String startDateString = "1/1/2020";
        String endDateString = "1/1/2023";

        SimpleDateFormat dfx = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date startDate = dfx.parse(startDateString);
            Date endDate = dfx.parse(endDateString);
            course = new Course("curso","descrição",startDate,endDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testEquals() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam1 = new RegularExam(specification, regularExamDate, course);
        RegularExam exam2 = new RegularExam(specification, regularExamDate, course);
        RegularExam exam3 = new RegularExam(specification, regularExamDate, course);

        // Verify
        assertTrue(exam1.equals(exam1)); // Same instance
        assertTrue(exam1.equals(exam2)); // Same attributes
        assertFalse(exam1.equals(null)); // Null comparison
    }

    @Test
    public void testHashCode() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam1 = new RegularExam(specification, regularExamDate, course);
        RegularExam exam2 = new RegularExam(specification, regularExamDate, course);

        // Verify
        assertEquals(exam1.hashCode(), exam2.hashCode());
    }

    @Test
    public void sameAs() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam1 = new RegularExam(specification, regularExamDate, course);
        RegularExam exam2 = new RegularExam(specification, regularExamDate, course);
        RegularExam exam3 = new RegularExam(specification, regularExamDate, course);
        String differentClass = "This is a different class";

        // Verify
        assertTrue(exam1.sameAs(exam1)); // Same instance
        assertTrue(exam1.sameAs(exam2)); // Same attributes
        assertFalse(exam1.sameAs(differentClass)); // Different instance
        assertFalse(exam1.sameAs(null)); // Null comparison
        assertFalse(exam1.sameAs(course)); // Different class
    }

    @Test
    public void compareTo() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam1 = new RegularExam(specification, regularExamDate, course);
        RegularExam exam2 = new RegularExam(specification, regularExamDate, course);

        // Verify
        assertEquals(0, exam1.compareTo(exam2.identity()));
    }

    @Test
    public void identity() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam = new RegularExam(specification, regularExamDate, course);

        // Verify
        assertEquals(exam.identity(), exam.identity());
    }

    @Test
    public void hasIdentity() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam = new RegularExam(specification, regularExamDate, course);

        // Verify
        assertTrue(exam.hasIdentity(exam.identity()));
        assertFalse(exam.hasIdentity(123)); // Different identity
    }

    @Test
    public void course() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam = new RegularExam(specification, regularExamDate, course);

        // Execute
        Course result = exam.course();

        // Verify
        assertEquals(course, result);
    }

    @Test
    public void regularExamDate() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam = new RegularExam(specification, regularExamDate, course);

        // Execute
        RegularExamDate result = exam.regularExamDate();

        // Verify
        assertEquals(regularExamDate, result);
    }

    @Test
    public void regularExamSpecification() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam = new RegularExam(specification, regularExamDate, course);

        // Execute
        RegularExamSpecification result = exam.regularExamSpecification();

        // Verify
        assertEquals(specification, result);
    }

    @Test
    public void testProtectedConstructor() {
        // Execute
        RegularExam exam = new RegularExam();

        // Verify
        assertNull(exam.regularExamSpecification());
        assertNull(exam.regularExamDate());
        assertNull(exam.course());
    }

    @Test
    public void updateRegularExamDate_ShouldUpdateDate() {
        RegularExamSpecification specification = new RegularExamSpecification("Sample Specification");
        RegularExam exam = new RegularExam(specification, regularExamDate, course);


        // Arrange
        RegularExamDate newDate = RegularExamDate.valueOf(regularExamDate.openDate(),regularExamDate.closeDate());

        // Act
        exam.updateRegularExamDate(newDate);

        // Assert
        assertEquals(newDate, exam.regularExamDate());
    }

    @Test
    public void updateRegularExamSpecification_ShouldUpdateSpecification() {
        // Arrange
        RegularExamSpecification newSpecification = new RegularExamSpecification("New Specification");
        RegularExam exam = new RegularExam(newSpecification, regularExamDate, course);
        // Act
        exam.updateRegularExamSpecification(newSpecification);

        // Assert
        assertEquals(newSpecification, exam.regularExamSpecification());
    }
}