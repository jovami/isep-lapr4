package eapli.base.examresult.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExamFeedbackPropertiesTest {
    @Test
    public void testToStringNone() {
        ExamFeedbackProperties property = ExamFeedbackProperties.NONE;
        assertEquals("Feedback not available", property.toString());
    }

    @Test
    public void testToStringOnSubmission() {
        ExamFeedbackProperties property = ExamFeedbackProperties.ON_SUBMISSION;
        assertEquals("Feedback available on submission", property.toString());
    }

    @Test
    public void testToStringAfterClosing() {
        ExamFeedbackProperties property = ExamFeedbackProperties.AFTER_CLOSING;
        assertEquals("Feedback available after closing of the exam", property.toString());
    }
}