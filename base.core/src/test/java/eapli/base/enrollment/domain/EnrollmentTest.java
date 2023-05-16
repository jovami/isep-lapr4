package eapli.base.enrollment.domain;


public class EnrollmentTest {

    /*
    private CourseName courseName;
    private Username username;

    @BeforeEach
    public void setUp() {
        courseName = new CourseName("JAVA-2");
        username = Username.valueOf("testUser");
    }

    @Test
    public void testCreateEnrollment() {
        Enrollment enrollment = new Enrollment(courseName, username);

        assertEquals(courseName.getName(), enrollment.obtainCourseName());
        assertEquals(username.toString(), enrollment.obtainUsername());
    }

    @Test
    void testShouldNotCreateEnrollmentWithNullCourseName() {
        assertThrows(IllegalArgumentException.class, () -> new Enrollment(null, username));
    }

    @Test
    void shouldNotCreateEnrollmentWithNullUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Enrollment(courseName, null));
    }

    @Test
    public void testChangeCourseName() {
        Enrollment enrollment = new Enrollment(courseName, username);

        CourseName newCourseName = new CourseName("Web Development");
        enrollment.changeCourseName(newCourseName);

        assertEquals(newCourseName.getName(), enrollment.obtainCourseName());
    }

    @Test
    void shouldNotChangeCourseNameToNull() {
        Enrollment enrollment = new Enrollment(courseName, username);
        assertThrows(IllegalArgumentException.class, () -> enrollment.changeCourseName(null));
    }

    @Test
    public void testChangeUsername() {
        Enrollment enrollment = new Enrollment(courseName, username);

        var newUsername = Username.valueOf("testUser2");
        enrollment.changeUsername(newUsername);

        assertEquals(newUsername.toString(), enrollment.obtainUsername());
    }

    @Test
    void shouldNotChangeUsernameToNull() {
        Enrollment enrollment = new Enrollment(courseName, username);
        assertThrows(IllegalArgumentException.class, () -> enrollment.changeUsername(null));
    }

    @Test
    void testSameAsWithDifferentEnrollment() {
        Enrollment enrollment = new Enrollment(courseName, username);
        Enrollment enrollment2 = new Enrollment(new CourseName("JAVA-3"), username);
        assertFalse(enrollment.sameAs(enrollment2));
    }

    @Test
    void testSameAsWithNull() {
        Enrollment enrollment = new Enrollment(courseName, username);
        assertFalse(enrollment.sameAs(null));
    }

    @Test
    void testSameAsWithSameEnrollment() {
        Enrollment enrollment = new Enrollment(courseName, username);
        assertTrue(enrollment.sameAs(enrollment));
    }

    @Test
    void testSameAsWithDifferentCourseName() {
        Enrollment enrollment = new Enrollment(courseName, username);
        Enrollment enrollment2 = new Enrollment(new CourseName("JAVA-3"), username);
        assertFalse(enrollment.sameAs(enrollment2));
    }

    @Test
    void testSameAsWithDifferentUsername() {
        Enrollment enrollment = new Enrollment(courseName, username);
        Enrollment enrollment2 = new Enrollment(courseName, Username.valueOf("testUser2"));
        assertFalse(enrollment.sameAs(enrollment2));
    }
    */
}
