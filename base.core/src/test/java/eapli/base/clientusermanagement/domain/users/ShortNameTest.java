package eapli.base.clientusermanagement.domain.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ShortNameTest {

    @Test
    public void createShortNameValidShortNameSucceeds() {
        String validShortName = "ABC";

        ShortName shortName = ShortName.valueOf(validShortName);

        assertNotNull(shortName);
        assertEquals(validShortName, shortName.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createShortNameNullShortNameThrowsException() {
        ShortName.valueOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createShortNameEmptyShortNameThrowsException() {
        ShortName.valueOf("");
    }

    @Test
    public void shortNameEqualitySameInstanceReturnsTrue() {
        String name = "ABC";
        ShortName shortName = new ShortName(name);

        boolean result = shortName.equals(shortName);

        assertTrue(result);
    }

    @Test
    public void shortNameEqualityNullObjectReturnsFalse() {
        String name = "ABC";
        ShortName shortName = new ShortName(name);

        boolean result = shortName.equals(null);

        assertFalse(result);
    }

    @Test
    public void shortNameEqualityDifferentClassReturnsFalse() {
        String name = "ABC";
        ShortName shortName = new ShortName(name);
        Object otherObject = new Object();

        boolean result = shortName.equals(otherObject);

        assertFalse(result);
    }

    @Test
    public void shortNameEqualityDifferentShortNameReturnsFalse() {
        ShortName shortName1 = new ShortName("ABC");
        ShortName shortName2 = new ShortName("DEF");

        boolean result = shortName1.equals(shortName2);

        assertFalse(result);
    }

    @Test
    public void compareToLessThanReturnsNegativeValue() {
        ShortName shortName1 = ShortName.valueOf("ABC");
        ShortName shortName2 = ShortName.valueOf("DEF");

        int result = shortName1.compareTo(shortName2);

        assertTrue(result < 0);
    }

    @Test
    public void compareToGreaterThanReturnsPositiveValue() {
        ShortName shortName1 = ShortName.valueOf("DEF");
        ShortName shortName2 = ShortName.valueOf("ABC");

        int result = shortName1.compareTo(shortName2);

        assertTrue(result > 0);
    }

    @Test
    public void compareToEqualReturnsZero() {
        ShortName shortName1 = ShortName.valueOf("ABC");
        ShortName shortName2 = ShortName.valueOf("ABC");

        int result = shortName1.compareTo(shortName2);

        assertEquals(0, result);
    }
}
