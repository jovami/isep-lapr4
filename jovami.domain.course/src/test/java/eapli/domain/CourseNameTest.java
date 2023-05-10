package eapli.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
}
