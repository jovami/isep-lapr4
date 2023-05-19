package eapli.base.formativeexam.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import eapli.base.course.domain.Course;

/**
 * FormativeExamTest
 */
public class FormativeExamTest {

    private Course MATEMATICA = null;

    @Before
    public void buildCourse() {
        final var sdf = new SimpleDateFormat("dd/MM/yyyy");

        final var name = "Matematica";
        final var description = "Funcoes e tabuada";
        final var minStudents = 10;
        final var maxStudents = 40;

        try {
            final var startDate = sdf.parse("20/03/2020");
            final var endDate = sdf.parse("20/09/2020");

            final var course = new Course(name, description, startDate, endDate);
            course.setCapacity(minStudents, maxStudents);

            MATEMATICA = course;
        } catch (ParseException e) {
            fail("Bad course");
        }
    }

    @Test
    public void ensureMustHaveCourse() {
        /* NOTE:
         * The validity of the specification itself is tested in the FormativeExamFactory
         * class since that's the only place it can actually be created in the normal
         * application, since constructors for FormativeExam and specification have
         * visibility ***protected***
         */
        var spec = new FormativeExamSpecification("test");

        assertThrows(IllegalArgumentException.class, () -> new FormativeExam(null, spec));
    }

    @Test
    public void ensureMustHaveSpecifiaction() {
        assertThrows(IllegalArgumentException.class, () -> new FormativeExam(MATEMATICA, null));
    }
}
