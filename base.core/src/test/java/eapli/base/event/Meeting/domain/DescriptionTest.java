package eapli.base.event.meeting.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void getDescription() {
        Description des = new Description("test");
        des.setDescription("set test");
        assertEquals("set test", des.getDescription());
    }

    @Test
    public void testEqualsNull() {
        Description des = new Description("test");
        assertFalse(des.equals(null));
    }

    @Test
    public void testEqualsObject() {
        Description des = new Description("test");
        assertFalse(des.equals(new Object()));
    }

    @Test
    public void testEqualsSelf() {
        Description des = new Description("test");
        assertTrue(des.equals(des));
    }

    @Test
    public void testEqualsDiffDes() {
        Description des = new Description("test");
        Description des2 = new Description("test2");
        assertFalse(des.equals(des2));
    }
}
