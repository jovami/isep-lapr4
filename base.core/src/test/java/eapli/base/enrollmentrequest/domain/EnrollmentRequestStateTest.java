package eapli.base.enrollmentrequest.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EnrollmentRequestStateTest {
    @Test
    public void testToStringApproved() {
        EnrollmentRequestState state = EnrollmentRequestState.APPROVED;
        assertEquals("Enrollment request approved", state.toString());
    }

    @Test
    public void testToStringDenied() {
        EnrollmentRequestState state = EnrollmentRequestState.DENIED;
        assertEquals("Enrollment request denied", state.toString());
    }

    @Test
    public void testToStringPending() {
        EnrollmentRequestState state = EnrollmentRequestState.PENDING;
        assertEquals("Enrollment request pending", state.toString());
    }
}
