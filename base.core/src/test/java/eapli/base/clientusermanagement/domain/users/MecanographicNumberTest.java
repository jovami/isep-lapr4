package eapli.base.clientusermanagement.domain.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MecanographicNumberTest {

    @Test
    public void mecanographicNumberCreationValidNumberSuccess() {
        String validNumber = "123456";

        MecanographicNumber mecanographicNumber = MecanographicNumber.valueOf(validNumber);

        assertEquals(validNumber, mecanographicNumber.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void mecanographicNumberCreationNullNumberThrowsException() {
        String nullNumber = null;

        MecanographicNumber.valueOf(nullNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mecanographicNumberCreationEmptyNumberThrowsException() {
        String emptyNumber = "";

        MecanographicNumber.valueOf(emptyNumber);
    }

    @Test
    public void mecanographicNumberEqualitySameNumberReturnsTrue() {
        String number = "123456";
        MecanographicNumber mecanographicNumber1 = new MecanographicNumber(number);
        MecanographicNumber mecanographicNumber2 = new MecanographicNumber(number);

        boolean result = mecanographicNumber1.equals(mecanographicNumber2);

        assertTrue(result);
    }

    @Test
    public void mecanographicNumberEqualityDifferentNumberReturnsFalse() {
        MecanographicNumber mecanographicNumber1 = new MecanographicNumber("123456");
        MecanographicNumber mecanographicNumber2 = new MecanographicNumber("654321");

        boolean result = mecanographicNumber1.equals(mecanographicNumber2);

        assertFalse(result);
    }

    @Test
    public void mecanographicNumberEqualitySameInstanceReturnsTrue() {
        String number = "123456";
        MecanographicNumber mecanographicNumber = new MecanographicNumber(number);

        boolean result = mecanographicNumber.equals(mecanographicNumber);

        assertTrue(result);
    }

    @Test
    public void mecanographicNumberEqualityNullObjectReturnsFalse() {
        String number = "123456";
        MecanographicNumber mecanographicNumber = new MecanographicNumber(number);

        boolean result = mecanographicNumber.equals(null);

        assertFalse(result);
    }

    @Test
    public void mecanographicNumberEqualityDifferentClassReturnsFalse() {
        String number = "123456";
        MecanographicNumber mecanographicNumber = new MecanographicNumber(number);
        Object otherObject = new Object();

        boolean result = mecanographicNumber.equals(otherObject);

        assertFalse(result);
    }

    @Test
    public void mecanographicNumberHashCodeSameNumberReturnsEqualHashCodes() {
        String number = "123456";
        MecanographicNumber mecanographicNumber1 = new MecanographicNumber(number);
        MecanographicNumber mecanographicNumber2 = new MecanographicNumber(number);

        int hashCode1 = mecanographicNumber1.hashCode();
        int hashCode2 = mecanographicNumber2.hashCode();

        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void mecanographicNumberToStringValidNumberReturnsNumberAsString() {
        String number = "123456";
        MecanographicNumber mecanographicNumber = new MecanographicNumber(number);

        String result = mecanographicNumber.toString();

        assertEquals(number, result);
    }

    @Test
    public void mecanographicNumberComparisonSameNumberReturnsZero() {
        String number = "123456";
        MecanographicNumber mecanographicNumber1 = new MecanographicNumber(number);
        MecanographicNumber mecanographicNumber2 = new MecanographicNumber(number);

        int result = mecanographicNumber1.compareTo(mecanographicNumber2);

        assertEquals(0, result);
    }

    @Test
    public void mecanographicNumberComparisonDifferentNumberReturnsNonZero() {
        MecanographicNumber mecanographicNumber1 = new MecanographicNumber("123456");
        MecanographicNumber mecanographicNumber2 = new MecanographicNumber("654321");

        int result = mecanographicNumber1.compareTo(mecanographicNumber2);

        assertNotEquals(0, result);
    }
}
