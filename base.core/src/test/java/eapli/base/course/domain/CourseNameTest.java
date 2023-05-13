package eapli.base.course.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseNameTest {

    @Test
    public void testNameEmpty(){
        CourseName name = new CourseName();
        assertNull(name.getName());
    }

    @Test
    public void testName(){
        CourseName name = new CourseName("Curso");
        assertEquals("Curso",name.getName());
        assertEquals("Curso",name.toString());
    }

    @Test
    public void testSetName(){
        CourseName name = new CourseName("Curso");
        name.setName("Curso2");
        assertEquals("Curso2",name.getName());
    }
    @Test
    public void checkHashCode(){
        CourseName name = new CourseName("Curso");
        CourseName name2 = new CourseName("Curso");
        assertEquals(name.hashCode(),name2.hashCode());
    }

    @Test
    public void equalsName(){
        CourseName name = new CourseName("Curso");
        assertTrue(name.equals(name));
    }
    @Test
    public void equalsNull(){
        CourseName name = new CourseName("Curso");
        assertFalse(name.equals(null));
    }
    @Test
    public void equalsDiffObject(){
        CourseName name = new CourseName("Curso");
        assertFalse(name.equals(new Object()));
    }
}
