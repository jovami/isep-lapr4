package eapli.base.clientusermanagement.domain.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TaxPayerNumberTest {

    @Test
    public void constructorValidNumberCreatesInstance() {
        String number = "123456789";

        TaxPayerNumber taxPayerNumber = TaxPayerNumber.valueOf(number);

        assertNotNull(taxPayerNumber);
        assertEquals(number, taxPayerNumber.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullNumberThrowsException() {
        String number = null;

        TaxPayerNumber.valueOf(number);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorEmptyNumberThrowsException() {
        String number = "";

        TaxPayerNumber.valueOf(number);
    }

    @Test(expected = IllegalStateException.class)
    public void constructorInvalidNumberFormatThrowsException() {
        String number = "1234";

        TaxPayerNumber.valueOf(number);
    }

    @Test
    public void compareToLessThanReturnsNegativeValue() {
        TaxPayerNumber number1 = TaxPayerNumber.valueOf("123456789");
        TaxPayerNumber number2 = TaxPayerNumber.valueOf("987654321");

        int result = number1.compareTo(number2);

        assertTrue(result < 0);
    }

    @Test
    public void compareToGreaterThanReturnsPositiveValue() {
        TaxPayerNumber number1 = TaxPayerNumber.valueOf("987654321");
        TaxPayerNumber number2 = TaxPayerNumber.valueOf("123456789");

        int result = number1.compareTo(number2);

        assertTrue(result > 0);
    }

    @Test
    public void compareToEqualReturnsZero() {
        TaxPayerNumber number1 = TaxPayerNumber.valueOf("123456789");
        TaxPayerNumber number2 = TaxPayerNumber.valueOf("123456789");

        int result = number1.compareTo(number2);

        assertEquals(0, result);
    }
}
