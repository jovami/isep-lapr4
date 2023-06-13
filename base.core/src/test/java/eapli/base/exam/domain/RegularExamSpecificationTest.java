package eapli.base.exam.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class RegularExamSpecificationTest {

    @Test
    public void specificationString() {
        // Prepare
        String specification = "This is a test specification";
        RegularExamSpecification regularExamSpecification = new RegularExamSpecification(specification);

        // Execute
        String result = regularExamSpecification.specification();

        // Verify
        Assertions.assertEquals(specification, result);
    }

    @Test
    public void testEquals() {
        // Prepare
        RegularExamSpecification spec1 = new RegularExamSpecification("Specification 1");
        RegularExamSpecification spec2 = new RegularExamSpecification("Specification 1");
        RegularExamSpecification spec3 = new RegularExamSpecification("Specification 2");

        // Verify
        Assertions.assertEquals(spec1, spec2);
        Assertions.assertNotEquals(spec1, spec3);
        Assertions.assertNotEquals(spec2, spec3);
    }

    @Test
    public void equalsSameInstance() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");

        // Execute and Verify
        assertTrue(specification.equals(specification));
    }

    @Test
    public void equalsNullObject() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");

        // Execute and Verify
        assertFalse(specification.equals(null));
    }

    @Test
    public void equalsDifferentClass() {
        // Prepare
        RegularExamSpecification specification = new RegularExamSpecification("Test Specification");
        String differentClass = "This is a different class";

        // Execute and Verify
        assertFalse(specification.equals(differentClass));
    }

    @Test
    public void testHashCode() {
        // Prepare
        RegularExamSpecification spec1 = new RegularExamSpecification("Specification 1");
        RegularExamSpecification spec2 = new RegularExamSpecification("Specification 1");

        // Verify
        Assertions.assertEquals(spec1.hashCode(), spec2.hashCode());
    }

    @Test
    public void testToString() {
        // Prepare
        String specification = "This is a test specification";
        RegularExamSpecification regularExamSpecification = new RegularExamSpecification(specification);
        String expected = String.format(specification);

        // Execute
        String result = regularExamSpecification.toString();

        // Verify
        Assertions.assertEquals(expected, result);
    }

}
