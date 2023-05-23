package eapli.base.question.domain;

import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseDescription;
import eapli.base.course.domain.CourseDuration;
import eapli.base.course.domain.CourseName;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuestionTest {
    private Course MATEMATICA = null;

    @Before
    public void buildCourse() {
        final var df = DateTimeFormatter.ofPattern("d/M/yyyy");

        final var name = "Matematica";
        final var description = "Funcoes e tabuada";
        final var minStudents = 10;
        final var maxStudents = 40;

        final var startDate = LocalDate.parse("20/03/2020", df);
        final var endDate = LocalDate.parse("20/09/2020", df);

        final var course = new Course(CourseName.valueOf(name), CourseDescription.valueOf(description), CourseDuration.valueOf(startDate, endDate));
        course.setCapacity(minStudents, maxStudents);

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
        var spec = new QuestionSpecification("test");

        assertThrows(IllegalArgumentException.class, () -> new Question(null, spec));
    }

    @Test
    public void ensureMustHaveSpecification() {
        assertThrows(IllegalArgumentException.class, () -> new Question(MATEMATICA, null));
    }
}
