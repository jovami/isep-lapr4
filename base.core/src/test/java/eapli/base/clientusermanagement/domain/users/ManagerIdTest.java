package eapli.base.clientusermanagement.domain.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ManagerIdTest {

    @Test
    public void managerIdCreationValidManagerIdSuccess() {
        String validId = "M1234";

        ManagerId managerId = ManagerId.valueOf(validId);

        assertEquals(validId, managerId.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void managerIdCreationNullManagerIdThrowsException() {
        String nullId = null;

        ManagerId.valueOf(nullId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void managerIdCreationEmptyManagerIdThrowsException() {
        String emptyId = "";

        ManagerId.valueOf(emptyId);
    }

    @Test(expected = IllegalStateException.class)
    public void managerIdCreationInvalidManagerIdThrowsException() {
        String invalidId = "M12!";

        ManagerId.valueOf(invalidId);
    }

    @Test
    public void managerIdComparisonSameManagerIdReturnsZero() {
        String id = "M1234";
        ManagerId managerId1 = new ManagerId(id);
        ManagerId managerId2 = new ManagerId(id);

        int result = managerId1.compareTo(managerId2);

        assertEquals(0, result);
    }

    @Test
    public void managerIdComparisonDifferentManagerIdReturnsNonZero() {
        ManagerId managerId1 = new ManagerId("M1234");
        ManagerId managerId2 = new ManagerId("M5678");

        int result = managerId1.compareTo(managerId2);

        assertNotEquals(0, result);
    }

    @Test
    public void managerIdToStringValidManagerIdReturnsIdAsString() {
        String id = "M1234";
        ManagerId managerId = new ManagerId(id);

        String result = managerId.toString();

        assertEquals(id, result);
    }

    @Test
    public void managerIdToStringNullManagerIdReturnsNullString() {
        ManagerId managerId = new ManagerId();

        String result = managerId.toString();

        assertNull(result);
    }
}
