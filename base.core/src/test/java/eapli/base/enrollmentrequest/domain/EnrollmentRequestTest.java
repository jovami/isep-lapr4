package eapli.base.enrollmentrequest.domain;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.StudentBuilder;
import eapli.base.course.domain.Course;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentRequestTest {
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;

    @BeforeEach
    public void setUp() throws ParseException {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date sDate = df.parse("20/05/2020");
        Date eDate = df.parse("20/09/2020");
        course1 = new Course("PYTHON-1", "Python for beginners :)", sDate, eDate);
        course2 = new Course("JAVA-3", "Java advanced!", sDate, eDate);

        var user1 = userBuilder.with("alexandre", "Password1", "Alexandre", "Moreira", "alexmoreira@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        var user2 = userBuilder.with("miguel", "Password1", "Miguel", "Novais", "mnovais672@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();

        final var studentBuilder = new StudentBuilder();
        studentBuilder.withSystemUser(user1).withMecanographicNumber("isep567").withFullName("Alexandre Moreira").
                withShortName("Alex").withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        student1 = studentBuilder.build();
        studentBuilder.withSystemUser(user2).withMecanographicNumber("isep568").withFullName("Miguel Novais").
                withShortName("Miguel").withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        student2 = studentBuilder.build();
    }

    @Test
     void courseEmptyThrowsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new EnrollmentRequest(null, student1);
        });
    }

    @Test
     void studentEmptyThrowsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new EnrollmentRequest(course1, null);
        });
    }

    @Test
     void createEnrollmentRequestShouldWork(){
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student1);
            assertEquals(student1, request.student());
            assertEquals(course1, request.course());
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student1);
            assertEquals(student1, request.student());
            assertEquals(course2, request.course());
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student2);
            assertEquals(student2, request.student());
            assertEquals(course1, request.course());
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student2);
            assertEquals(student2, request.student());
            assertEquals(course2, request.course());
        }
    }

    @Test
     void sameAsReturnsTrueWhenComparedToItself() {
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student1);
            assertTrue(request.sameAs(request));
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student1);
            assertTrue(request.sameAs(request));
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student2);
            assertTrue(request.sameAs(request));
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student2);
            assertTrue(request.sameAs(request));
        }
    }

    @Test
     void sameAsReturnsTrueWhenComparedToIdenticalRequest() {
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student2);
            EnrollmentRequest identicalRequest = new EnrollmentRequest(course1, student2);
            assertTrue(request.sameAs(identicalRequest));
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student2);
            EnrollmentRequest identicalRequest = new EnrollmentRequest(course2, student2);
            assertTrue(request.sameAs(identicalRequest));
        }
    }

    @Test
     void sameAsReturnsFalseWhenComparedToRequestWithDifferentCourse() {
        EnrollmentRequest differentCourse = new EnrollmentRequest(course1, student1);
        EnrollmentRequest request = new EnrollmentRequest(course2, student1);
        assertFalse(request.sameAs(differentCourse));

    }

    @Test
     void sameAsReturnsFalseWhenComparedToRequestWithDifferentStudent() {
        EnrollmentRequest request = new EnrollmentRequest(course1, student1);
        EnrollmentRequest differentStudent = new EnrollmentRequest(course1, student2);
        assertFalse(request.sameAs(differentStudent));
    }

    @Test
     void sameAsReturnsFalseWhenComparedToNonEnrollmentRequestObject() {
        EnrollmentRequest request = new EnrollmentRequest(course1, student1);
        Object notAnEnrollmentRequest = new Object();
        assertFalse(request.sameAs(notAnEnrollmentRequest));
    }

    @Test
     void newEnrollmentRequestHasPendingState() {
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student1);
            assertEquals(EnrollmentRequestState.PENDING, request.enrollmentRequestState());
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student1);
            assertEquals(EnrollmentRequestState.PENDING, request.enrollmentRequestState());
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student2);
            assertEquals(EnrollmentRequestState.PENDING, request.enrollmentRequestState());
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student2);
            assertEquals(EnrollmentRequestState.PENDING, request.enrollmentRequestState());
        }
    }

    @Test
     void approveEnrollmentRequestChangesStateToApproved() {
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student1);
            request.approveEnrollmentRequest();
            assertEquals(EnrollmentRequestState.APPROVED, request.enrollmentRequestState());
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student1);
            request.approveEnrollmentRequest();
            assertEquals(EnrollmentRequestState.APPROVED, request.enrollmentRequestState());
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student2);
            request.approveEnrollmentRequest();
            assertEquals(EnrollmentRequestState.APPROVED, request.enrollmentRequestState());
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student2);
            request.approveEnrollmentRequest();
            assertEquals(EnrollmentRequestState.APPROVED, request.enrollmentRequestState());
        }
    }

    @Test
     void denyEnrollmentRequestChangesStateToDenied() {
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student1);
            request.denyEnrollmentRequest("No available seats in the course");
            assertEquals(EnrollmentRequestState.DENIED, request.enrollmentRequestState());
            request.deniedReason().right().map(reason -> {
                assertEquals("No available seats in the course", reason.obtainDenyingReason());
                return null;
            });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student1);
            request.denyEnrollmentRequest("No available seats in the course");
            assertEquals(EnrollmentRequestState.DENIED, request.enrollmentRequestState());
            request.deniedReason().right().map(reason -> {
                assertEquals("No available seats in the course", reason.obtainDenyingReason());
                return null;
            });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student2);
            request.denyEnrollmentRequest("No available seats in the course");
            assertEquals(EnrollmentRequestState.DENIED, request.enrollmentRequestState());
            request.deniedReason().right().map(reason -> {
                assertEquals("No available seats in the course", reason.obtainDenyingReason());
                return null;
            });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student2);
            request.denyEnrollmentRequest("No available seats in the course");
            assertEquals(EnrollmentRequestState.DENIED, request.enrollmentRequestState());
            request.deniedReason().right().map(reason -> {
                assertEquals("No available seats in the course", reason.obtainDenyingReason());
                return null;
            });
        }
    }

    @Test
     void cannotApproveDeniedEnrollmentRequest() {
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student1);
            request.denyEnrollmentRequest("No available seats in the course");
            request.approveEnrollmentRequest().left().map(enrollmentRequest -> {
                assertEquals("Enrollment request was already denied", enrollmentRequest);
                return null;
            });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student1);
            request.denyEnrollmentRequest("No available seats in the course");
            request.approveEnrollmentRequest().left().map(enrollmentRequest -> {
                assertEquals("Enrollment request was already denied", enrollmentRequest);
                return null;
            });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student2);
            request.denyEnrollmentRequest("No available seats in the course");
            request.approveEnrollmentRequest().left().map(enrollmentRequest -> {
                assertEquals("Enrollment request was already denied", enrollmentRequest);
                return null;
            });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student2);
            request.denyEnrollmentRequest("No available seats in the course");
            request.approveEnrollmentRequest().left().map(enrollmentRequest -> {
                assertEquals("Enrollment request was already denied", enrollmentRequest);
                return null;
            });
        }
    }

    @Test
     void cannotApproveApprovedEnrollmentRequest() {
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student1);
            request.approveEnrollmentRequest();
            request.approveEnrollmentRequest().left().map(enrollmentRequest -> {
                assertEquals("Enrollment request was already approved", enrollmentRequest);
                return null;
            });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student1);
            request.approveEnrollmentRequest();
            request.approveEnrollmentRequest().left().map(enrollmentRequest -> {
                assertEquals("Enrollment request was already approved", enrollmentRequest);
                return null;
            });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student2);
            request.approveEnrollmentRequest();
            request.approveEnrollmentRequest().left().map(enrollmentRequest -> {
                assertEquals("Enrollment request was already approved", enrollmentRequest);
                return null;
            });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student2);
            request.approveEnrollmentRequest();
            request.approveEnrollmentRequest().left().map(enrollmentRequest -> {
                assertEquals("Enrollment request was already approved", enrollmentRequest);
                return null;
            });
        }
    }

    @Test
     void cannotDenyApprovedEnrollmentRequest() {
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student1);
            request.approveEnrollmentRequest();
            request.denyEnrollmentRequest("No available seats in the course")
                    .left().map(enrollmentRequest -> {
                        assertEquals("Enrollment request was already approved", enrollmentRequest);
                        return null;
                    });
        }
{
            EnrollmentRequest request = new EnrollmentRequest(course2, student1);
            request.approveEnrollmentRequest();
            request.denyEnrollmentRequest("No available seats in the course")
                    .left().map(enrollmentRequest -> {
                        assertEquals("Enrollment request was already approved", enrollmentRequest);
                        return null;
                    });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student2);
            request.approveEnrollmentRequest();
            request.denyEnrollmentRequest("No available seats in the course")
                    .left().map(enrollmentRequest -> {
                        assertEquals("Enrollment request was already approved", enrollmentRequest);
                        return null;
                    });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student2);
            request.approveEnrollmentRequest();
            request.denyEnrollmentRequest("No available seats in the course")
                    .left().map(enrollmentRequest -> {
                        assertEquals("Enrollment request was already approved", enrollmentRequest);
                        return null;
                    });
        }
    }

    @Test
     void cannotDenyDeniedEnrollmentRequest() {
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student1);
            request.denyEnrollmentRequest("No available seats in the course");
            request.denyEnrollmentRequest("No available seats in the course")
                    .left().map(enrollmentRequest -> {
                        assertEquals("Enrollment request was already denied", enrollmentRequest);
                        return null;
                    });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student1);
            request.denyEnrollmentRequest("No available seats in the course");
            request.denyEnrollmentRequest("No available seats in the course")
                    .left().map(enrollmentRequest -> {
                        assertEquals("Enrollment request was already denied", enrollmentRequest);
                        return null;
                    });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student2);
            request.denyEnrollmentRequest("No available seats in the course");
            request.denyEnrollmentRequest("No available seats in the course")
                    .left().map(enrollmentRequest -> {
                        assertEquals("Enrollment request was already denied", enrollmentRequest);
                        return null;
                    });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student2);
            request.denyEnrollmentRequest("No available seats in the course");
            request.denyEnrollmentRequest("No available seats in the course")
                    .left().map(enrollmentRequest -> {
                        assertEquals("Enrollment request was already denied", enrollmentRequest);
                        return null;
                    });
        }
    }

    @Test
     void cannotGetDeniedReasonFromNotDeniedEnrollmentRequest(){
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student1);
            request.deniedReason().left().map(enrollmentRequest -> {
                assertEquals("Enrollment request was not denied", enrollmentRequest);
                return null;
            });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student1);
            request.deniedReason().left().map(enrollmentRequest -> {
                assertEquals("Enrollment request was not denied", enrollmentRequest);
                return null;
            });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course1, student2);
            request.deniedReason().left().map(enrollmentRequest -> {
                assertEquals("Enrollment request was not denied", enrollmentRequest);
                return null;
            });
        }
        {
            EnrollmentRequest request = new EnrollmentRequest(course2, student2);
            request.deniedReason().left().map(enrollmentRequest -> {
                assertEquals("Enrollment request was not denied", enrollmentRequest);
                return null;
            });
        }
    }
}

