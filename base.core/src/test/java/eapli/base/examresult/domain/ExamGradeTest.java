package eapli.base.examresult.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Test;

public class ExamGradeTest {

    public void ensureGradeCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(-25.f, 20.f));
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(-1.f, 2420.24f));
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(-0.25f, 21.2f));
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(-1203.0f, 21.21f));
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(-20.0f, 10.44f));

        assertDoesNotThrow(() -> new ExamGrade(0.f, 0.f));
        assertDoesNotThrow(() -> new ExamGrade(0.f, 12.f));
        assertDoesNotThrow(() -> new ExamGrade(0.f, 20.f));
        assertDoesNotThrow(() -> new ExamGrade(0.f, 25.25f));

        assertDoesNotThrow(() -> new ExamGrade(24.f, 25.25f));
        assertDoesNotThrow(() -> new ExamGrade(13.25f, 25.25f));
        assertDoesNotThrow(() -> new ExamGrade(10.25f, 25.25f));
        assertDoesNotThrow(() -> new ExamGrade(2.75f, 21.21f));
        assertDoesNotThrow(() -> new ExamGrade(1.50f, 25.25f));
    }

    public void ensureMaxGradeCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(20.f, -25.f));
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(2420.24f, -1.f));
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(21.2f, -0.25f));
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(21.21f, -1203.0f));
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(10.44f, -20.0f));

        assertDoesNotThrow(() -> new ExamGrade(0.f, 0.f));
        assertDoesNotThrow(() -> new ExamGrade(0.f, 12.f));
        assertDoesNotThrow(() -> new ExamGrade(0.f, 20.f));
        assertDoesNotThrow(() -> new ExamGrade(0.f, 25.25f));

        assertDoesNotThrow(() -> new ExamGrade(24.f, 25.25f));
        assertDoesNotThrow(() -> new ExamGrade(13.25f, 25.25f));
        assertDoesNotThrow(() -> new ExamGrade(10.25f, 25.25f));
        assertDoesNotThrow(() -> new ExamGrade(2.75f, 21.21f));
        assertDoesNotThrow(() -> new ExamGrade(1.50f, 25.25f));
    }

    public void ensureMaxGradeCannotBeLessThanGrade() {
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(12.f, 0.f));
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(25.25f, 24.f));
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(25.25f, 13.25f));
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(25.25f, 10.25f));
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(21.21f, 2.75f));
        assertThrows(IllegalArgumentException.class, () -> new ExamGrade(25.25f, 1.50f));

        assertDoesNotThrow(() -> new ExamGrade(0.f, 0.f));
        assertDoesNotThrow(() -> new ExamGrade(0.f, 12.f));
        assertDoesNotThrow(() -> new ExamGrade(24.f, 25.25f));
        assertDoesNotThrow(() -> new ExamGrade(13.25f, 25.25f));
        assertDoesNotThrow(() -> new ExamGrade(10.25f, 25.25f));
        assertDoesNotThrow(() -> new ExamGrade(2.75f, 21.21f));
        assertDoesNotThrow(() -> new ExamGrade(1.50f, 25.25f));
    }

    @Test
    public void ensureConstructorWorksInNormalSituations() {
        var grade = new ExamGrade(16.5f, 20.0f);
        assertEquals(16.5F, grade.grade(), 0.01);
    }

    @Test
    public void ensureEqualsAndHashCodeHasExpected() {
        var grade1 = new ExamGrade(19F, 20.F);
        var grade2 = new ExamGrade(19F, 20.F);
        var grade3 = new ExamGrade(14.5F, 20.F);

        assertEquals(grade1, grade1);
        assertEquals(grade1, grade2);
        assertNotEquals(grade1, grade3);

        assertEquals(grade1.hashCode(), grade2.hashCode());
        assertNotEquals(grade1.hashCode(), grade3.hashCode());
    }

    @Test
    public void ensureEqualsReturnsFalseIfComparedWithNull() {
        var grade = new ExamGrade(13.f, 14.f);
        assertNotEquals(grade, null);
    }

    @Test
    public void ensureEqualsReturnsFalseIfComparedWithAnotherClass() {
        var grade = new ExamGrade(14, 20.f);
        assertNotEquals(grade, "I'm not an ExamGrade :/, I'm a string");
    }

}
