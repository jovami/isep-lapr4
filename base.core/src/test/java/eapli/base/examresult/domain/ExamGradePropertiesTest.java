package eapli.base.examresult.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExamGradePropertiesTest {
    @Test
    public void testToStringNone() {
        ExamGradeProperties property = ExamGradeProperties.NONE;
        assertEquals("Grade not available", property.toString());
    }

    @Test
    public void testToStringOnSubmission() {
        ExamGradeProperties property = ExamGradeProperties.ON_SUBMISSION;
        assertEquals("Grade available on submission", property.toString());
    }

    @Test
    public void testToStringAfterClosing() {
        ExamGradeProperties property = ExamGradeProperties.AFTER_CLOSING;
        assertEquals("Grade available after closing of the exam", property.toString());
    }
}
