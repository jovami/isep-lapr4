package eapli.base.enrollmentrequest.domain;

import eapli.base.course.domain.CourseName;
import eapli.framework.infrastructure.authz.domain.model.Username;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentRequestTest {

    Username user1;
    Username user2;

    @BeforeEach
    public void setUp() {
        user1 = Username.valueOf("ruben");
        user2 = Username.valueOf("diogo");
    }

    @Test
    public void testCourseNameEmptyThrowsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new EnrollmentRequest(null, user1);
        });
    }

    @Test
    public void testUsernameEmptyThrowsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new EnrollmentRequest(new CourseName("JAVA-1"), null);
        });
    }

    @Test
    public void testEnrollmentRequestNormalUse(){
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        assertEquals(new CourseName("JAVA-1"), request.getCourseName());
        assertEquals(user1, request.getUsername());
    }

    @Test
    public void testSameAsReturnsTrueWhenComparedToItself() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("Math-2"), user1);
        assertTrue(request.sameAs(request));
    }

    @Test
    public void testSameAsReturnsTrueWhenComparedToIdenticalRequest() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("PYTHON-1"), user2);
        EnrollmentRequest identicalRequest = new EnrollmentRequest(new CourseName("PYTHON-1"), user2);
        assertTrue(request.sameAs(identicalRequest));
    }

    @Test
    public void testSameAsReturnsFalseWhenComparedToRequestWithDifferentCourseName() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("ENGLISH-C1"), user1);
        EnrollmentRequest differentCourseName = new EnrollmentRequest(new CourseName("ENGLISH-C2"), user1);
        assertFalse(request.sameAs(differentCourseName));
    }

    @Test
    public void testSameAsReturnsFalseWhenComparedToRequestWithDifferentUsername() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("ENGLISH-C1"), user1);
        EnrollmentRequest differentUsername = new EnrollmentRequest(new CourseName("ENGLISH-C1"), user2);
        assertFalse(request.sameAs(differentUsername));
    }

    @Test
    public void testSameAsReturnsFalseWhenComparedToNonEnrollmentRequestObject() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("ENGLISH-C1"), user1);
        Object notAnEnrollmentRequest = new Object();
        assertFalse(request.sameAs(notAnEnrollmentRequest));
    }

    @Test
    public void testNewEnrollmentRequestHasPendingState() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        assertEquals(EnrollmentRequestState.PENDING, request.getEnrollmentRequestState());
    }

    @Test
    public void testApproveEnrollmentRequestChangesStateToApproved() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        request.approveEnrollmentRequest();
        assertEquals(EnrollmentRequestState.APPROVED, request.getEnrollmentRequestState());
    }

    @Test
    public void testDenyEnrollmentRequestChangesStateToDenied() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        request.denyEnrollmentRequest("No available seats in the course");
        assertEquals(EnrollmentRequestState.DENIED, request.getEnrollmentRequestState());
        request.getDeniedReason().right().map(reason -> {
            assertEquals("No available seats in the course", reason);
            return null;
        });
    }

    @Test
    public void testCannotApproveDeniedEnrollmentRequest() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        request.denyEnrollmentRequest("No available seats in the course");
        request.approveEnrollmentRequest().left().map(enrollmentRequest -> {
            assertEquals("Enrollment request was already denied", enrollmentRequest);
            return null;
        });
    }

    @Test
    public void testCannotApproveApprovedEnrollmentRequest() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        request.approveEnrollmentRequest();
        request.approveEnrollmentRequest().left().map(enrollmentRequest -> {
            assertEquals("Enrollment request was already approved", enrollmentRequest);
            return null;
        });
    }

    @Test
    public void testCannotDenyApprovedEnrollmentRequest() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        request.approveEnrollmentRequest();
        request.denyEnrollmentRequest("No available seats in the course")
                .left().map(enrollmentRequest -> {
                    assertEquals("Enrollment request was already approved", enrollmentRequest);
                    return null;
                });
    }

    @Test
    public void testCannotDenyDeniedEnrollmentRequest() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        request.denyEnrollmentRequest("No available seats in the course");
        request.denyEnrollmentRequest("No available seats in the course")
                .left().map(enrollmentRequest -> {
                    assertEquals("Enrollment request was already denied", enrollmentRequest);
                    return null;
                });
    }

    @Test
    public void testCannotGetDeniedReasonFromNotDeniedEnrollmentRequest(){
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        request.getDeniedReason().left().map(enrollmentRequest -> {
            assertEquals("Enrollment request was not denied", enrollmentRequest);
            return null;
        });
    }

}