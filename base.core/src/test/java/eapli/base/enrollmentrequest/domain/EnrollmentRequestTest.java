package eapli.base.enrollmentrequest.domain;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.StudentBuilder;
import eapli.base.course.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.functional.Either;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import eapli.framework.validations.Preconditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnrollmentRequestTest {

    private Course course;
    private Student student;
    private String startDate = "20/05/2020";
    private String endDate = "20/09/2020";
    private SystemUser user;
    private final String username = "Tony";
    private final String mecanographicNumber = "isep567";
    private final String fullName = "Tony Stark";
    private final String shortName = "Tony";
    private final String dateOfBirth = "2001-01-01";
    private final String taxPayerNumber = "123756789";
    private final EnrollmentRequestState state = EnrollmentRequestState.PENDING;
    private final String deniedReason = "Not enough credits";
    private EnrollmentRequest enrollmentRequest;

    @BeforeEach
    void setUp() throws ParseException {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date sDate = df.parse(startDate);
        Date eDate = df.parse(endDate);
        course = new Course("Fisics", "Fisics dos materiais", sDate, eDate);
        user = userBuilder.with(username, "Password1", "dummy", "dummy", "a@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        final var studentBuilder = new StudentBuilder();
        studentBuilder.withSystemUser(user).withMecanographicNumber(mecanographicNumber).withFullName(fullName).
                withShortName(shortName).withDateOfBirth(dateOfBirth).withTaxPayerNumber(taxPayerNumber);
        student = studentBuilder.build();
        enrollmentRequest = new EnrollmentRequest(course, student);

    }
}

/*


    @Test
     void testCourseNameEmptyThrowsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new EnrollmentRequest(null, student1);
        });
    }

    @Test
     void testUsernameEmptyThrowsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new EnrollmentRequest(course1, null);
        });
    }

    @Test
     void testEnrollmentRequestNormalUse(){
        EnrollmentRequest request = new EnrollmentRequest(course1, student1);
        assertEquals(student1, request.getStudent());
    }

    @Test
     void testSameAsReturnsTrueWhenComparedToItself() {
        EnrollmentRequest request = new EnrollmentRequest(course1, student1);
        assertTrue(request.sameAs(request));
    }

    @Test
     void testSameAsReturnsTrueWhenComparedToIdenticalRequest() {
        EnrollmentRequest request = new EnrollmentRequest(course1, student2);
        EnrollmentRequest identicalRequest = new EnrollmentRequest(course1, student2);
        assertTrue(request.sameAs(identicalRequest));
    }

    /*@Test
     void testSameAsReturnsFalseWhenComparedToRequestWithDifferentCourseName() {
        EnrollmentRequest request = new EnrollmentRequest(course2, student1);
        EnrollmentRequest differentCourseName = new EnrollmentRequest(course2, student1);
        assertFalse(request.sameAs(differentCourseName));
    }

    @Test
     void testSameAsReturnsFalseWhenComparedToRequestWithDifferentUsername() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("ENGLISH-C1"), user1);
        EnrollmentRequest differentUsername = new EnrollmentRequest(new CourseName("ENGLISH-C1"), user2);
        assertFalse(request.sameAs(differentUsername));
    }

    @Test
     void testSameAsReturnsFalseWhenComparedToNonEnrollmentRequestObject() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("ENGLISH-C1"), user1);
        Object notAnEnrollmentRequest = new Object();
        assertFalse(request.sameAs(notAnEnrollmentRequest));
    }

    @Test
     void testNewEnrollmentRequestHasPendingState() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        assertEquals(EnrollmentRequestState.PENDING, request.getEnrollmentRequestState());
    }

    @Test
     void testApproveEnrollmentRequestChangesStateToApproved() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), student1);
        request.approveEnrollmentRequest();
        assertEquals(EnrollmentRequestState.APPROVED, request.getEnrollmentRequestState());
    }

    @Test
     void testDenyEnrollmentRequestChangesStateToDenied() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        request.denyEnrollmentRequest("No available seats in the course");
        assertEquals(EnrollmentRequestState.DENIED, request.getEnrollmentRequestState());
        request.getDeniedReason().right().map(reason -> {
            assertEquals("No available seats in the course", reason);
            return null;
        });
    }

    @Test
     void testCannotApproveDeniedEnrollmentRequest() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        request.denyEnrollmentRequest("No available seats in the course");
        request.approveEnrollmentRequest().left().map(enrollmentRequest -> {
            assertEquals("Enrollment request was already denied", enrollmentRequest);
            return null;
        });
    }

    @Test
     void testCannotApproveApprovedEnrollmentRequest() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        request.approveEnrollmentRequest();
        request.approveEnrollmentRequest().left().map(enrollmentRequest -> {
            assertEquals("Enrollment request was already approved", enrollmentRequest);
            return null;
        });
    }

    @Test
     void testCannotDenyApprovedEnrollmentRequest() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        request.approveEnrollmentRequest();
        request.denyEnrollmentRequest("No available seats in the course")
                .left().map(enrollmentRequest -> {
                    assertEquals("Enrollment request was already approved", enrollmentRequest);
                    return null;
                });
    }

    @Test
     void testCannotDenyDeniedEnrollmentRequest() {
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        request.denyEnrollmentRequest("No available seats in the course");
        request.denyEnrollmentRequest("No available seats in the course")
                .left().map(enrollmentRequest -> {
                    assertEquals("Enrollment request was already denied", enrollmentRequest);
                    return null;
                });
    }

    @Test
     void testCannotGetDeniedReasonFromNotDeniedEnrollmentRequest(){
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), user1);
        request.getDeniedReason().left().map(enrollmentRequest -> {
            assertEquals("Enrollment request was not denied", enrollmentRequest);
            return null;
        });
    }*/

