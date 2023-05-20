package eapli.base.course.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CourseNameTest {

    @Test
    public void testNameEmpty() {
        CourseName name = new CourseName();
        assertNull(name.name());
    }

    @Test
    public void testName() {
        CourseName name = new CourseName("Curso");
        assertEquals("Curso", name.name());
        assertEquals("Curso", name.toString());
    }

    @Test
    public void checkHashCode() {
        CourseName name = new CourseName("Curso");
        CourseName name2 = new CourseName("Curso");
        assertEquals(name.hashCode(), name2.hashCode());
    }

    @Test
    public void equalsName() {
        CourseName name = new CourseName("Curso");
        assertTrue(name.equals(name));
    }

    @Test
    public void equalsNull() {
        CourseName name = new CourseName("Curso");
        assertFalse(name.equals(null));
    }

    @Test
    public void equalsDiffObject() {
        CourseName name = new CourseName("Curso");
        assertFalse(name.equals(new Object()));
    }
}
