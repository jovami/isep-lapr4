package eapli.base.board.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardRowTest {

    @Test
    public void testGetColumnTitle() {
        BoardRow row = new BoardRow();
        row.setRowTitle("Test Column");
        String columnTitle = row.getRowTitle();

        assertEquals("Test Column", columnTitle);
    }

    @Test
    public void testSetColumnTitle() {
        BoardRow row = new BoardRow();
        row.setRowTitle("New Column Title");

        assertEquals("New Column Title", row.getRowTitle());
    }
}