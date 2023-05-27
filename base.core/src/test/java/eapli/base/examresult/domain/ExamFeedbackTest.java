package eapli.base.examresult.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExamFeedbackTest {

    @Test
    public void ensureConstructorWorksInNormalSituations() {
        var feedback = new ExamFeedback("Your exam seems to exceed my expectations!!");
        assertEquals("Your exam seems to exceed my expectations!!", feedback.feedback());
    }

    @Test
    public void ensureConstructorDoesntAcceptNullValues() {
        assertThrows(IllegalArgumentException.class, () -> new ExamFeedback(null));
    }

    @Test
    public void ensureEqualsAndHashCodeHasExpected() {
        var feedback1 = new ExamFeedback("Your exam seems to exceed my expectations!!");
        var feedback2 = new ExamFeedback("Your exam seems to exceed my expectations!!");
        var feedback3 = new ExamFeedback("Your exam seems to be below my expectations!!");

        assertEquals(feedback1, feedback1);
        assertEquals(feedback1, feedback2);
        assertNotEquals(feedback1, feedback3);

        assertEquals(feedback1.hashCode(), feedback2.hashCode());
        assertNotEquals(feedback1.hashCode(), feedback3.hashCode());
    }

    @Test
    public void ensureEqualsReturnsFalseIfComparedWithNull() {
        var feedback = new ExamFeedback("Your exam seems to exceed my expectations!!");
        assertNotEquals(feedback, null);
    }

    @Test
    public void ensureEqualsReturnsFalseIfComparedWithAnotherClass() {
        var feedback = new ExamFeedback("Your exam seems to exceed my expectations!!");
        assertNotEquals(feedback, "I'm not an ExamFeedback :/, I'm a string");
    }

}