package eapli.base.clientusermanagement.domain.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AcronymTest {

    @Test
    public void acronymCreationValidAcronymSuccess() {
        String validAcronym = "ABC";
        Acronym acronym = new Acronym(validAcronym);
        assertEquals(validAcronym, acronym.toString());
    }

    @Test
    public void acronymCreationEmptyAcronymThrowsException() {
        String emptyAcronym = "";
        assertThrows(IllegalArgumentException.class, () -> new Acronym(emptyAcronym));
    }

    @Test
    public void acronymCreationInvalidAcronymFormatThrowsException() {
        String invalidAcronym = "abc";
        assertThrows(IllegalStateException.class, () -> new Acronym(invalidAcronym));
    }

    @Test
    public void acronymComparisonSameAcronymsReturnsZero() {
        Acronym acronym1 = new Acronym("ABC");
        Acronym acronym2 = new Acronym("ABC");
        int result = acronym1.compareTo(acronym2);
        assertEquals(0, result);
    }

    @Test
    public void acronymComparisonDifferentAcronymsReturnsNonZero() {
        Acronym acronym1 = new Acronym("ABC");
        Acronym acronym2 = new Acronym("XYZ");
        int result = acronym1.compareTo(acronym2);
        assertNotEquals(0, result);
    }

    @Test
    public void equalsMethodSameObjectReturnsTrue() {
        Acronym acronym = new Acronym("ABC");
        assertTrue(acronym.equals(acronym));
    }

    @Test
    public void equalsMethodNullObjectReturnsFalse() {
        Acronym acronym = new Acronym("ABC");
        assertFalse(acronym.equals(null));
    }

    @Test
    public void equalsMethodDifferentClassReturnsFalse() {
        Acronym acronym = new Acronym("ABC");
        assertFalse(acronym.equals("ABC"));
    }

    @Test
    public void equalsMethodEqualAcronymsReturnsTrue() {
        Acronym acronym1 = new Acronym("ABC");
        Acronym acronym2 = new Acronym("ABC");
        assertTrue(acronym1.equals(acronym2));
    }

    @Test
    public void equalsMethodDifferentAcronymsReturnsFalse() {
        Acronym acronym1 = new Acronym("ABC");
        Acronym acronym2 = new Acronym("XYZ");
        assertFalse(acronym1.equals(acronym2));
    }

    @Test
    public void equalsMethodNullAcronymReturnsFalse() {
        Acronym acronym1 = new Acronym("ABC");
        assertFalse(acronym1.equals(null));
    }

    @Test
    public void hashCodeMethodSameAcronymsReturnsSameHashCode() {
        Acronym acronym1 = new Acronym("ABC");
        Acronym acronym2 = new Acronym("ABC");
        assertEquals(acronym1.hashCode(), acronym2.hashCode());
    }

    @Test
    public void hashCodeMethodDifferentAcronymsReturnsDifferentHashCodes() {
        Acronym acronym1 = new Acronym("ABC");
        Acronym acronym2 = new Acronym("XYZ");
        assertNotEquals(acronym1.hashCode(), acronym2.hashCode());
    }

    @Test
    public void valueOfMethodValidAcronymReturnsAcronymObject() {
        String validAcronym = "ABC";
        Acronym acronym = Acronym.valueOf(validAcronym);
        assertNotNull(acronym);
        assertEquals(validAcronym, acronym.toString());
    }
}
