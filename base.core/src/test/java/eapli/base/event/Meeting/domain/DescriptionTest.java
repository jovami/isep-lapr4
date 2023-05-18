package eapli.base.event.Meeting.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DescriptionTest {

    @Test
    void getDescription() {
        Description des = new Description("test");
        des.setDescription("set test");
        assertEquals("set test",des.getDescription());
    }

    @Test
    void testEqualsNull() {
        Description des = new Description("test");
        assertFalse(des.equals(null));
    }

    @Test
    void testEqualsObject() {
        Description des = new Description("test");
        assertFalse(des.equals(new Object()));
    }
    @Test
    void testEqualsSelf() {
        Description des = new Description("test");
        assertTrue(des.equals(des));
    }

    @Test
    void testEqualsDiffDes() {
        Description des = new Description("test");
        Description des2 = new Description("test2");
        assertFalse(des.equals(des2));
    }
}
