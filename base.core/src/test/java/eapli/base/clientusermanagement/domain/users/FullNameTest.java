package eapli.base.clientusermanagement.domain.users;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FullNameTest {

    @Test
    public void fullNameCreationValidFullNameSuccess() {
        String[] validNames = { "John", "Doe" };

        FullName fullName = FullName.valueOf(validNames);

        assertEquals("John Doe", fullName.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void fullNameCreationNullFullNameThrowsException() {
        String[] nullNames = null;

        FullName.valueOf(nullNames);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fullNameCreationEmptyFullNameThrowsException() {
        String[] emptyNames = {};

        FullName.valueOf(emptyNames);
    }

    @Test(expected = IllegalStateException.class)
    public void fullNameCreationInvalidFullNameThrowsException() {
        String[] invalidNames = { "John123", "Doe" };

        FullName.valueOf(invalidNames);
    }

    @Test
    public void fullNameToStringValidFullNameReturnsFormattedString() {
        String[] validNames = { "John", "Doe" };
        FullName fullName = new FullName(validNames);
        String expectedString = "John Doe";

        String result = fullName.toString();

        assertEquals(expectedString, result);
    }
}
