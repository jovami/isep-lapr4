package eapli.base.clientusermanagement.domain.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.StudentBuilder;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;

public class StudentTest {
    private Student student1;
    private Student student2;
    private SystemUser user1;
    private SystemUser user2;

    @Before
    public void setUp() {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());

        user1 = userBuilder.with("alexandre", "Password1", "Alexandre", "Moreira", "alexmoreira@gmail.com")
                .withRoles(BaseRoles.STUDENT).build();
        user2 = userBuilder.with("miguel", "Password1", "Miguel", "Novais", "mnovais672@gmail.com")
                .withRoles(BaseRoles.STUDENT).build();

        final var studentBuilder = new StudentBuilder();
        studentBuilder.withSystemUser(user1).withMecanographicNumber("isep567").withFullName("Alexandre Moreira")
                .withShortName("Alex").withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        student1 = studentBuilder.build();
        studentBuilder.withSystemUser(user2).withMecanographicNumber("isep568").withFullName("Miguel Novais")
                .withShortName("Miguel").withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        student2 = studentBuilder.build();
    }

    @Test
    public void testCreateStudent_ValidParameters_SuccessfullyCreated() {
        assertNotNull(student1);
        assertNotNull(student2);
    }

    @Test
    public void testEquals_SameInstance_ReturnsTrue() {
        assertEquals(student1, student1);
        assertEquals(student2, student2);
    }

    @Test
    public void testEquals_SameValues_ReturnsTrue() {
        {
            Student student1Copy = new Student(student1.user(), student1.mecanographicNumber(), student1.fullName(),
                    student1.shortName(), student1.dateOfBirth(), student1.taxPayerNumber());
            assertEquals(student1, student1Copy);
        }
        {
            Student student2Copy = new Student(student2.user(), student2.mecanographicNumber(), student2.fullName(),
                    student2.shortName(), student2.dateOfBirth(), student2.taxPayerNumber());
            assertEquals(student2, student2Copy);
        }
    }

    @Test
    public void testEquals_DifferentInstancesDifferentValues_ReturnsFalse() {
        assertNotEquals(student1, student2);
    }

    @Test
    public void testHashCode_SameValues_ReturnsSameHashCode() {
        {
            Student student1Copy = new Student(student1.user(), student1.mecanographicNumber(), student1.fullName(),
                    student1.shortName(), student1.dateOfBirth(), student1.taxPayerNumber());
            assertEquals(student1.hashCode(), student1Copy.hashCode());
        }
        {
            Student student2Copy = new Student(student2.user(), student2.mecanographicNumber(), student2.fullName(),
                    student2.shortName(), student2.dateOfBirth(), student2.taxPayerNumber());
            assertEquals(student2.hashCode(), student2Copy.hashCode());
        }
    }

    @Test
    public void testHashCode_DifferentValues_ReturnsDifferentHashCode() {
        assertNotEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    public void testSameAs_SameInstance_ReturnsTrue() {
        assertTrue(student1.sameAs(student1));
        assertTrue(student2.sameAs(student2));
    }

    @Test
    public void testSameAs_SameValues_ReturnsTrue() {
        {
            Student student1Copy = new Student(student1.user(), student1.mecanographicNumber(), student1.fullName(),
                    student1.shortName(), student1.dateOfBirth(), student1.taxPayerNumber());
            assertTrue(student1.sameAs(student1Copy));
        }
        {
            Student student2Copy = new Student(student2.user(), student2.mecanographicNumber(), student2.fullName(),
                    student2.shortName(), student2.dateOfBirth(), student2.taxPayerNumber());
            assertTrue(student2.sameAs(student2Copy));
        }
    }

    @Test
    public void testSameAs_DifferentInstancesDifferentValues_ReturnsFalse() {
        assertFalse(student1.sameAs(student2));
    }
}
