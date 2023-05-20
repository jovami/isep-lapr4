package eapli.base.enrollmentrequest.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class DeniedReasonTest {
    @Test
    public void testConstructorAndGetDenyingReason() {
        DeniedReason deniedReason = new DeniedReason("You do not meet the eligibility criteria");
        assertEquals("You do not meet the eligibility criteria", deniedReason.obtainDenyingReason());
    }

    @Test
    public void testSetAndGetDenyingReason() {
        DeniedReason deniedReason = new DeniedReason();
        deniedReason.specifyDeniedReason("You have an incomplete application");
        assertEquals("You have an incomplete application", deniedReason.obtainDenyingReason());
    }

    @Test
    public void testEqualsAndHashCode() {
        DeniedReason deniedReason1 = new DeniedReason("You do not meet the eligibility criteria");
        DeniedReason deniedReason2 = new DeniedReason("You do not meet the eligibility criteria");
        DeniedReason deniedReason3 = new DeniedReason("Your qualifications do not meet our standards");

        // Test for equality
        assertEquals(deniedReason1, deniedReason2);
        assertNotEquals(deniedReason1, deniedReason3);

        // Test for hash code
        assertEquals(deniedReason1.hashCode(), deniedReason2.hashCode());
        assertNotEquals(deniedReason1.hashCode(), deniedReason3.hashCode());
    }
}
