package eapli.base.exam.domain.regular_exam;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class RegularExamSpecificationTest {

    @Test
    public void valueOf() {
        // Prepare
        String specification = "This is a test specification";
        RegularExamSpecification expected = new RegularExamSpecification(specification);

        List<String> lines = new ArrayList<>();
        lines.add(specification);

        // Execute
        RegularExamSpecification result = RegularExamSpecification.valueOf(lines);

        // Verify
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testValueOf() throws IOException {
        // Prepare
        String filePath = "path/to/specification.txt";
        String specification = "This is a test specification";
        File file = new File(filePath);
        FileUtils.writeStringToFile(file, specification, StandardCharsets.UTF_8);
        RegularExamSpecification expected = new RegularExamSpecification(specification);

        // Execute
        RegularExamSpecification result = RegularExamSpecification.valueOf(file);

        // Verify
        Assertions.assertEquals(expected, result);

        // Cleanup
        FileUtils.deleteQuietly(file);
    }

    @Test
    public void specificationString() {
        // Prepare
        String specification = "This is a test specification";
        RegularExamSpecification regularExamSpecification = new RegularExamSpecification(specification);

        // Execute
        String result = regularExamSpecification.specificationString();

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

    @Test
    public void protectedConstructor()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // Obtain the constructor
        Constructor<RegularExamSpecification> constructor = RegularExamSpecification.class.getDeclaredConstructor();

        // Ensure accessibility of the constructor
        constructor.setAccessible(true);

        // Create an instance using the protected constructor
        RegularExamSpecification instance = constructor.newInstance();

        // Verify that the specification is null
        assertNull(instance.specificationString());
        constructor.setAccessible(false);
    }

}
