package eapli.base.examresult.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExamGradeTest {

    @Test
    public void ensureConstructorWorksInNormalSituations() {
        var grade = new ExamGrade(16.5F);
        assertEquals(16.5F, grade.grade(), 0.01);
    }

    @Test
    public void ensureEqualsAndHashCodeHasExpected() {
        var grade1 = new ExamGrade(19F);
        var grade2 = new ExamGrade(19F);
        var grade3 = new ExamGrade(14.5F);

        assertEquals(grade1, grade1);
        assertEquals(grade1, grade2);
        assertNotEquals(grade1, grade3);

        assertEquals(grade1.hashCode(), grade2.hashCode());
        assertNotEquals(grade1.hashCode(), grade3.hashCode());
    }

    @Test
    public void ensureEqualsReturnsFalseIfComparedWithNull() {
        var grade = new ExamGrade(13);
        assertNotEquals(grade, null);
    }

    @Test
    public void ensureEqualsReturnsFalseIfComparedWithAnotherClass() {
        var grade = new ExamGrade(14);
        assertNotEquals(grade, "I'm not an ExamGrade :/, I'm a string");
    }

}