package eapli.base.course.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.TeacherBuilder;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;

class StaffMemberTest {

    private StaffMember member;
    private Course course;

    @BeforeEach
    void setUp() {

        String taxPayerNumber = "123123123";
        String dateOfBirth = "2003-10-10";

        SystemUser user = createSystemUser("userName", "Password1", "First", "Last", "user@email.com",
                BaseRoles.TEACHER);
        Teacher teacher = createTeacher(user, "TCH", dateOfBirth, "Full", "Short", taxPayerNumber);
        course = createCourse("1/1/2023", "1/1/2024", "curso", "descricao");

        member = new StaffMember(course, teacher);
    }

    public Teacher createTeacher(SystemUser user, String acronym, String dateOfBirth, String fullName, String shortName,
            String taxPayerNumber) {
        TeacherBuilder teacherBuilder = new TeacherBuilder().withSystemUser(user).withAcronym(acronym)
                .withDateOfBirth(dateOfBirth)
                .withFullName(fullName).withTaxPayerNumber(taxPayerNumber).withShortName(shortName);
        return teacherBuilder.build();
    }

    public SystemUser createSystemUser(String userName, String password, String firsName, String lastName, String email,
            final Role... roles) {

        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        userBuilder.with(userName, password, firsName, lastName, email).withRoles(roles);

        return userBuilder.build();
    }

    public Course createCourse(String startDateString, String endDateString, String name, String description) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Course course;

        try {
            Date startDate = df.parse(startDateString);
            Date endDate = df.parse(endDateString);
            course = new Course(name, description, startDate, endDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return course;
    }

    @Test
    void course() {
        Course courseNew = createCourse("1/1/2023", "1/1/2024", "curso diff", "curso novo");
        member.setCourse(courseNew);
        assertEquals(courseNew, member.course());
    }

    @Test
    void member() {
        String taxPayerNumber = "123123123";
        String dateOfBirth = "2003-10-10";
        SystemUser user = createSystemUser("newUser", "Password1", "first", "last", "newuser@email.com");
        Teacher teacher = createTeacher(user, "NEW", "2000-10-10", "FULL", "SHORT", "222222222");
        member.setMember(teacher);
        assertEquals(teacher, member.member());
    }

    @Test
    void sameAsNull() {
        assertFalse(member.sameAs(null));
    }

    @Test
    void sameAsObject() {
        assertFalse(member.sameAs(new Object()));
    }

    @Test
    void sameAsSelf() {
        assertTrue(member.sameAs(member));
    }

    @Test
    void sameAsDiffName() {
        SystemUser user = createSystemUser("newUser", "Password1", "first", "last", "newuser@email.com");
        Teacher teacher = createTeacher(user, "NEW", "2000-10-10", "FULL", "SHORT", "222222222");
        StaffMember newMember = new StaffMember(course, teacher);
        assertFalse(member.sameAs(newMember));
    }

    @Test
    void compareToEqual() {
        assertEquals(0, member.compareTo(0));
    }

    @Test
    void compareToBigger() {
        assertEquals(-1, member.compareTo(10));
    }

    @Test
    void compareToLower() {
        assertEquals(1, member.compareTo(-10));
    }

    @Test
    void hasIdentity() {
        assertTrue(member.hasIdentity(0));
    }
}
