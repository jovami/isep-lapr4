package eapli.base.enrollmentrequest.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EnrollmentRequestStateTest {

    @Test
    public void testToStringApproved() {
        EnrollmentRequestState state = EnrollmentRequestState.APPROVED;
        Assertions.assertEquals("Enrollment request approved", state.toString());
    }

    @Test
    public void testToStringDenied() {
        EnrollmentRequestState state = EnrollmentRequestState.DENIED;
        Assertions.assertEquals("Enrollment request denied", state.toString());
    }

    @Test
    public void testToStringPending() {
        EnrollmentRequestState state = EnrollmentRequestState.PENDING;
        Assertions.assertEquals("Enrollment request pending", state.toString());
    }

}