package eapli.base.board.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardParticipantPermissionsTest {

    @Test
    public void testToString_Write() {
        BoardParticipantPermissions permission = BoardParticipantPermissions.WRITE;
        String actualString = permission.toString();

        assertEquals("Write", actualString);
    }

    @Test
    public void testToString_Read() {
        BoardParticipantPermissions permission = BoardParticipantPermissions.READ;
        String actualString = permission.toString();

        assertEquals("Read", actualString);
    }
}