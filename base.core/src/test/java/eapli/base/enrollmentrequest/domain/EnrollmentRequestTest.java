package eapli.base.enrollmentrequest.domain;

class EnrollmentRequestTest {

    /*
    Student student1;
    Student student2;
    Course course1;
    Course course2;

    @BeforeEach
    public void setUp() {
        SystemUser user1 = getNewDummyUser();
        student1 = new Student(user1, new MecanographicNumber("12345678"), new FullName("Alberto Faria Lopes"), new ShortName("Alberto Lopes"), new DateOfBirth(LocalDate.now()), new TaxPayerNumber("123456789"));

        SystemUser user2 = getNewDummyUserTwo();
        student2 = new Student(user2, new MecanographicNumber("12378678"), new FullName("Joao Carlos Lopes"), new ShortName("Joao Lopes"), new DateOfBirth(LocalDate.now()), new TaxPayerNumber("123457949"));

        course1 = new Course("JAVA-1", "JA1", new Date("01/01/2020"), new Date("01/01/2021"));
    }

    public static SystemUser dummyUser(final String username, final Role... roles) {
        // should we load from spring context?
        final SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with(username, "duMMy1", "dummy", "dummy", "a@b.ro").withRoles(roles).build();
    }

    private SystemUser getNewDummyUser() {
        return dummyUser("dummy", BaseRoles.MANAGER);
    }

    private SystemUser getNewDummyUserTwo() {
        return dummyUser("dummy-two", BaseRoles.MANAGER);
    }

    @Test
    public void testCourseNameEmptyThrowsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new EnrollmentRequest(null, student1);
        });
    }

    @Test
    public void testUsernameEmptyThrowsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new EnrollmentRequest(course1, null);
        });
    }

    @Test
    public void testEnrollmentRequestNormalUse(){
        EnrollmentRequest request = new EnrollmentRequest(course1, student1);
        assertEquals(student1, request.getStudent());
    }

    @Test
    public void testSameAsReturnsTrueWhenComparedToItself() {
        EnrollmentRequest request = new EnrollmentRequest(course1, student1);
        assertTrue(request.sameAs(request));
    }

    @Test
    public void testSameAsReturnsTrueWhenComparedToIdenticalRequest() {
        EnrollmentRequest request = new EnrollmentRequest(course1, student2);
        EnrollmentRequest identicalRequest = new EnrollmentRequest(course1, student2);
        assertTrue(request.sameAs(identicalRequest));
    }

    /*@Test
    public void testSameAsReturnsFalseWhenComparedToRequestWithDifferentCourseName() {
        EnrollmentRequest request = new EnrollmentRequest(course2, student1);
        EnrollmentRequest differentCourseName = new EnrollmentRequest(course2, student1);
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
        EnrollmentRequest request = new EnrollmentRequest(new CourseName("JAVA-1"), student1);
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
    }*/

}