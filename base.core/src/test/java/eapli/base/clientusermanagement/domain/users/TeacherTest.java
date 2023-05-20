package eapli.base.clientusermanagement.domain.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;

public class TeacherTest {
    private Teacher teacher1;
    private Teacher teacher2;
    private SystemUser user1;
    private SystemUser user2;

    @Before
    public void setUp() {
        user1 = createUser("john", "Password1", "John", "Doe", "john.doe@example.com");
        user2 = createUser("jane", "Password1", "Jane", "Smith", "jane.smith@example.com");

        teacher1 = createTeacher(user1, "ABC", "John Doe", "John", "1990-01-01", "123456789");
        teacher2 = createTeacher(user2, "DEF", "Jane Smith", "Jane", "1995-02-15", "987654321");
    }

    @Test
    public void createTeacherValidParametersSuccessfullyCreated() {
        assertNotNull(teacher1);
        assertNotNull(teacher2);
    }

    @Test
    public void equalsSameInstanceReturnsTrue() {
        assertEquals(teacher1, teacher1);
        assertEquals(teacher2, teacher2);
    }

    @Test
    public void equalsSameValuesReturnsTrue() {
        Teacher teacher1Copy = createTeacher(user1, "ABC", "John Doe", "John", "1990-01-01", "123456789");
        assertEquals(teacher1, teacher1Copy);

        Teacher teacher2Copy = createTeacher(user2, "DEF", "Jane Smith", "Jane", "1995-02-15", "987654321");
        assertEquals(teacher2, teacher2Copy);
    }

    @Test
    public void equalsDifferentInstancesDifferentValuesReturnsFalse() {
        assertNotEquals(teacher1, teacher2);
    }

    @Test
    public void hashCodeSameValuesReturnsSameHashCode() {
        Teacher teacher1Copy = createTeacher(user1, "ABC", "John Doe", "John", "1990-01-01", "123456789");
        assertEquals(teacher1.hashCode(), teacher1Copy.hashCode());

        Teacher teacher2Copy = createTeacher(user2, "DEF", "Jane Smith", "Jane", "1995-02-15", "987654321");
        assertEquals(teacher2.hashCode(), teacher2Copy.hashCode());
    }

    @Test
    public void hashCodeDifferentValuesReturnsDifferentHashCode() {
        assertNotEquals(teacher1.hashCode(), teacher2.hashCode());
    }

    @Test
    public void sameAsSameInstanceReturnsTrue() {
        assertTrue(teacher1.sameAs(teacher1));
        assertTrue(teacher2.sameAs(teacher2));
    }

    @Test
    public void sameAsSameValuesReturnsTrue() {
        Teacher teacher1Copy = createTeacher(user1, "ABC", "John Doe", "John", "1990-01-01", "123456789");
        assertTrue(teacher1.sameAs(teacher1Copy));

        Teacher teacher2Copy = createTeacher(user2, "DEF", "Jane Smith", "Jane", "1995-02-15", "987654321");
        assertTrue(teacher2.sameAs(teacher2Copy));
    }

    @Test
    public void sameAsDifferentInstancesDifferentValuesReturnsFalse() {
        assertFalse(teacher1.sameAs(teacher2));
    }

    private SystemUser createUser(String username, String password, String firstName, String lastName, String email) {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with(username, password, firstName, lastName, email).build();
    }

    private Teacher createTeacher(SystemUser user, String acronym, String fullName, String shortName,
            String dateOfBirth, String taxPayerNumber) {
        Acronym teacherAcronym = new Acronym(acronym);
        FullName teacherFullName = new FullName(fullName);
        ShortName teacherShortName = new ShortName(shortName);
        DateOfBirth teacherDateOfBirth = DateOfBirth.valueOf(dateOfBirth);
        TaxPayerNumber teacherTaxPayerNumber = new TaxPayerNumber(taxPayerNumber);

        return new Teacher(user, teacherAcronym, teacherFullName, teacherShortName, teacherDateOfBirth,
                teacherTaxPayerNumber);
    }

}
