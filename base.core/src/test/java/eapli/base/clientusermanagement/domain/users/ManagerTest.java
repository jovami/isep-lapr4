package eapli.base.clientusermanagement.domain.users;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.base.clientusermanagement.usermanagement.domain.ManagerBuilder;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;

public class ManagerTest {
    private Manager manager1;
    private Manager manager2;
    private SystemUser systemUser1;
    private SystemUser systemUser2;
    private FullName fullName1;
    private FullName fullName2;
    private ShortName shortName1;
    private ShortName shortName2;
    private DateOfBirth dateOfBirth1;
    private DateOfBirth dateOfBirth2;
    private TaxPayerNumber taxPayerNumber1;
    private TaxPayerNumber taxPayerNumber2;

    @Before
    public void setUp() throws ParseException {
        SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        systemUser1 = userBuilder.with("alexandre", "Password1", "Alexandre", "Moreira", "alexmoreira@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();
        systemUser2 = userBuilder.with("miguel", "Password1", "Miguel", "Novais", "mnovais672@gmail.com")
                .withRoles(BaseRoles.MANAGER).build();

        final var managerBuilder = new ManagerBuilder();
        managerBuilder.withSystemUser(systemUser1).withFullName("Alexandre Moreira").withShortName("Alex")
                .withDateOfBirth("2001-01-01").withTaxPayerNumber("123756799");
        manager1 = managerBuilder.build();
        managerBuilder.withSystemUser(systemUser2).withFullName("Miguel Novais").withShortName("Miguel")
                .withDateOfBirth("2001-01-01").withTaxPayerNumber("123756789");
        manager2 = managerBuilder.build();

        fullName1 = new FullName("Alexandre Moreira");
        fullName2 = new FullName("Miguel Novais");
        shortName1 = new ShortName("Alex");
        shortName2 = new ShortName("Miguel");
        dateOfBirth1 = DateOfBirth.valueOf("2001-01-01");
        dateOfBirth2 = DateOfBirth.valueOf("2001-01-01");
        taxPayerNumber1 = new TaxPayerNumber("123756799");
        taxPayerNumber2 = new TaxPayerNumber("123756789");
    }

    @Test
    public void createManagerValidParametersSuccessfullyCreated() {
        {
            assertNotNull(manager1);
            assertEquals(systemUser1, manager1.user());
            assertEquals(fullName1.toString(), manager1.fullName().toString());
            assertEquals(shortName1.toString(), manager1.shortName().toString());
            assertEquals(dateOfBirth1.toString(), manager1.dateOfBirth().toString());
            assertEquals(taxPayerNumber1.toString(), manager1.taxPayerNumber().toString());
        }
        {
            assertNotNull(manager2);
            assertEquals(systemUser2, manager2.user());
            assertEquals(fullName2.toString(), manager2.fullName().toString());
            assertEquals(shortName2.toString(), manager2.shortName().toString());
            assertEquals(dateOfBirth2.toString(), manager2.dateOfBirth().toString());
            assertEquals(taxPayerNumber2.toString(), manager2.taxPayerNumber().toString());
        }
    }

    @Test
    public void testCreateManagerValidParametersSuccessfullyCreated() {
        assertNotNull(manager1);
        assertNotNull(manager2);
    }

    @Test
    public void testEqualsSameInstanceReturnsTrue() {
        assertEquals(manager1, manager1);
    }

    @Test
    public void testEqualsSameValuesReturnsTrue() {
        Manager manager1Copy = new Manager(manager1.user(), manager1.fullName(), manager1.shortName(),
                manager1.dateOfBirth(), manager1.taxPayerNumber());
        assertEquals(manager1, manager1Copy);
    }

    @Test
    public void testHashCodeSameValuesReturnsSameHashCode() {
        Manager manager1Copy = new Manager(manager1.user(), manager1.fullName(), manager1.shortName(),
                manager1.dateOfBirth(), manager1.taxPayerNumber());
        assertEquals(manager1.hashCode(), manager1Copy.hashCode());
    }

    @Test
    public void testSameAsSameInstanceReturnsTrue() {
        assertTrue(manager1.sameAs(manager1));
    }

    @Test
    public void testSameAs_SameValues_ReturnsTrue() {
        Manager manager1Copy = new Manager(manager1.user(), manager1.fullName(), manager1.shortName(),
                manager1.dateOfBirth(), manager1.taxPayerNumber());
        assertTrue(manager1.sameAs(manager1Copy));
    }

    @Test
    public void testSameAsDifferentInstancesDifferentValuesReturnsFalse() {
        assertFalse(manager1.sameAs(manager2));
    }

    @Test
    public void testToStringReturnsValidStringRepresentation() {
        String expected = "Manager\n" +
                "Manager Id: " + manager1.identity() + "\n" +
                "Full Name: " + manager1.fullName() + "\n" +
                "Short Name: " + manager1.shortName() + "\n" +
                "Date Of Birth: " + manager1.dateOfBirth() + "\n" +
                "Tax Payer Number: " + manager1.taxPayerNumber();
        assertEquals(expected, manager1.toString());
    }
}
