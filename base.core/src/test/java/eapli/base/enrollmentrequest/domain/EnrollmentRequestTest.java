package eapli.base.enrollmentrequest.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.StudentBuilder;
import eapli.base.course.domain.Course;
import eapli.base.course.domain.CourseFactory;
import eapli.base.course.dto.CreateCourseDTO;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;

public class EnrollmentRequestTest {
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;

    @Before
    public void setUp() throws ParseException {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        var df = DateTimeFormatter.ofPattern("d/M/yyyy");

        var sDate = LocalDate.parse("20/05/2020", df);
        var eDate = LocalDate.parse("20/09/2020", df);

        var dto1 = new CreateCourseDTO("PYTHON", 1L, "Python for beginners :)", sDate, eDate, 10, 24);
        var dto2 = new CreateCourseDTO("JAVA", 3L, "Java advanced!", sDate, eDate, 20, 34);

        course1 = new CourseFactory().build(dto1);
        course2 = new CourseFactory().build(dto2);

        var user1 = userBuilder.with("alexandre", "Password1", "Alexandre", "Moreira", "alexmoreira@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        var user2 = userBuilder.with("miguel", "Password1", "Miguel", "Novais", "mnovais672@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();

        final var studentBuilder = new StudentBuilder();
        studentBuilder.withSystemUser(user1).withMecanographicNumber("isep567").withFullName("Alexandre Moreira")
                .withShortName("Alex").withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        student1 = studentBuilder.build();
        studentBuilder.withSystemUser(user2).withMecanographicNumber("isep568").withFullName("Miguel Novais")
                .withShortName("Miguel").withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        student2 = studentBuilder.build();
    }

    @Test
    public void courseEmptyThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new EnrollmentRequest(null, student1));
    }

    @Test
    public void studentEmptyThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new EnrollmentRequest(course1, null));
    }

    @Test
    public void createEnrollmentRequestShouldWork() {
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
    public void sameAsReturnsTrueWhenComparedToItself() {
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
    public void sameAsReturnsTrueWhenComparedToIdenticalRequest() {
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
    public void sameAsReturnsFalseWhenComparedToRequestWithDifferentCourse() {
        EnrollmentRequest differentCourse = new EnrollmentRequest(course1, student1);
        EnrollmentRequest request = new EnrollmentRequest(course2, student1);
        assertFalse(request.sameAs(differentCourse));

    }

    @Test
    public void sameAsReturnsFalseWhenComparedToRequestWithDifferentStudent() {
        EnrollmentRequest request = new EnrollmentRequest(course1, student1);
        EnrollmentRequest differentStudent = new EnrollmentRequest(course1, student2);
        assertFalse(request.sameAs(differentStudent));
    }

    @Test
    public void sameAsReturnsFalseWhenComparedToNonEnrollmentRequestObject() {
        EnrollmentRequest request = new EnrollmentRequest(course1, student1);
        Object notAnEnrollmentRequest = new Object();
        assertFalse(request.sameAs(notAnEnrollmentRequest));
    }

    @Test
    public void newEnrollmentRequestHasPendingState() {
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
    public void approveEnrollmentRequestChangesStateToApproved() {
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
    public void denyEnrollmentRequestChangesStateToDenied() {
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
    public void cannotApproveDeniedEnrollmentRequest() {
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
    public void cannotApproveApprovedEnrollmentRequest() {
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
    public void cannotDenyApprovedEnrollmentRequest() {
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
    public void cannotDenyDeniedEnrollmentRequest() {
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
    public void cannotGetDeniedReasonFromNotDeniedEnrollmentRequest() {
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
