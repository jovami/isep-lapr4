package eapli.base.exam.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseFactory;
import eapli.base.course.dto.CreateCourseDTO;

public class RegularExamTest {

    private RegularExamDate regularExamDate;
    private Course course;

    @Before
    public void BeforeEach() {
        String openDateString = "10/10/2023 16:00";
        String closeDateString = "10/10/2023 18:00";

        var df = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

        var openDate = LocalDateTime.parse(openDateString, df);
        var closeDate = LocalDateTime.parse(closeDateString, df);
        regularExamDate = RegularExamDate.valueOf(openDate, closeDate);

        String startDateString = "01/01/2020";
        String endDateString = "01/01/2023";

        var dfx = DateTimeFormatter.ofPattern("d/M/yyyy");

        var startDate = LocalDate.parse(startDateString, dfx);
        var endDate = LocalDate.parse(endDateString, dfx);

        var dto = new CreateCourseDTO("curso", 1L, "descrição", startDate, endDate, 10, 24);
        course = new CourseFactory().build(dto);
    }

    @Test
    public void testEquals() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam1 = new RegularExam(RegularExamTitle.valueOf("test"),specification, regularExamDate, course);
        RegularExam exam2 = new RegularExam(RegularExamTitle.valueOf("test"),specification, regularExamDate, course);

        // Verify
        assertTrue(exam1.equals(exam1)); // Same instance
        assertTrue(exam1.equals(exam2)); // Same attributes
        assertFalse(exam1.equals(null)); // Null comparison
    }

    @Test
    public void testHashCode() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam1 = new RegularExam(RegularExamTitle.valueOf("test"), specification, regularExamDate, course);
        RegularExam exam2 = new RegularExam(RegularExamTitle.valueOf("test"), specification, regularExamDate, course);

        // Verify
        assertEquals(exam1.hashCode(), exam2.hashCode());
    }

    @Test
    public void sameAs() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam1 = new RegularExam(RegularExamTitle.valueOf("test"), specification, regularExamDate, course);
        RegularExam exam2 = new RegularExam(RegularExamTitle.valueOf("test"), specification, regularExamDate, course);
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
        RegularExam exam1 = new RegularExam(RegularExamTitle.valueOf("test"), specification, regularExamDate, course);
        RegularExam exam2 = new RegularExam(RegularExamTitle.valueOf("test"), specification, regularExamDate, course);

        // Verify
        assertEquals(0, exam1.compareTo(exam2.identity()));
    }

    @Test
    public void identity() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam = new RegularExam(RegularExamTitle.valueOf("test"), specification, regularExamDate, course);

        // Verify
        assertEquals(exam.identity(), exam.identity());
    }

    @Test
    public void hasIdentity() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam = new RegularExam(RegularExamTitle.valueOf("test"), specification, regularExamDate, course);

        // Verify
        assertTrue(exam.hasIdentity(exam.identity()));
        assertFalse(exam.hasIdentity(RegularExamTitle.valueOf("different"))); // Different identity
    }

    @Test
    public void course() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam = new RegularExam(RegularExamTitle.valueOf("test"), specification, regularExamDate, course);

        // Execute
        Course result = exam.course();

        // Verify
        assertEquals(course, result);
    }

    @Test
    public void regularExamDate() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam = new RegularExam(RegularExamTitle.valueOf("test"), specification, regularExamDate, course);

        // Execute
        RegularExamDate result = exam.date();

        // Verify
        assertEquals(regularExamDate, result);
    }

    @Test
    public void regularExamSpecification() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        RegularExam exam = new RegularExam(RegularExamTitle.valueOf("test"), specification, regularExamDate, course);

        // Execute
        RegularExamSpecification result = exam.specification();

        // Verify
        assertEquals(specification, result);
    }

    @Test
    public void testProtectedConstructor() {
        // Execute
        RegularExam exam = new RegularExam();

        // Verify
        assertNull(exam.specification());
        assertNull(exam.date());
        assertNull(exam.course());
    }

    @Test
    public void updateRegularExamDate_ShouldUpdateDate() {
        RegularExamSpecification specification = new RegularExamSpecification("Sample Specification");
        RegularExam exam = new RegularExam(RegularExamTitle.valueOf("test"), specification, regularExamDate, course);

        // Arrange
        RegularExamDate newDate = RegularExamDate.valueOf(regularExamDate.openDate(), regularExamDate.closeDate());

        // Act
        exam.updateDate(newDate);

        // Assert
        assertEquals(newDate, exam.date());
    }

    @Test
    public void updateRegularExamSpecification_ShouldUpdateSpecification() {
        // Arrange
        RegularExamSpecification newSpecification = new RegularExamSpecification("New Specification");
        RegularExam exam = new RegularExam(RegularExamTitle.valueOf("test"), newSpecification, regularExamDate, course);
        // Act
        exam.updateSpecification(newSpecification);

        // Assert
        assertEquals(newSpecification, exam.specification());
    }
}
