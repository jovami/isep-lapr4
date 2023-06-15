package eapli.base.formativeexam.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseFactory;
import eapli.base.course.dto.CreateCourseDTO;

/**
 * FormativeExamTest
 */
public class FormativeExamTest {

    private Course MATEMATICA = null;
    private final FormativeExamTitle TITLE = FormativeExamTitle.valueOf("Teste");

    @Before
    public void buildCourse() {
        final var df = DateTimeFormatter.ofPattern("d/M/yyyy");

        final var name = "Matematica";
        final var description = "Funcoes e tabuada";
        final var minStudents = 10;
        final var maxStudents = 40;

        final var startDate = LocalDate.parse("20/03/2020", df);
        final var endDate = LocalDate.parse("20/09/2020", df);

        final var dto = new CreateCourseDTO(name, 1L, description, startDate, endDate, minStudents, maxStudents);
        final var course = new CourseFactory().build(dto);

        MATEMATICA = course;
    }

    @Test
    public void ensureMustHaveCourse() {
        /*
         * NOTE:
         * The validity of the specification itself is tested in the
         * FormativeExamFactory
         * class since that's the only place it can actually be created in the normal
         * application, since constructors for FormativeExam and specification have
         * visibility ***protected***
         */
        var spec = new FormativeExamSpecification("test");

        assertThrows(IllegalArgumentException.class, () -> new FormativeExam(TITLE, null, spec));

        assertDoesNotThrow(() -> new FormativeExam(TITLE, MATEMATICA, spec));
    }

    @Test
    public void ensureMustHaveSpecifiaction() {
        assertThrows(IllegalArgumentException.class, () -> new FormativeExam(TITLE, MATEMATICA, null));
    }
}
